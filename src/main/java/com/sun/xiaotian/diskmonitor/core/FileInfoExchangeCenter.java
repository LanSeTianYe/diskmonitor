package com.sun.xiaotian.diskmonitor.core;

import com.sun.xiaotian.diskmonitor.model.FileSize;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件信息交换中心
 */

@Component
public class FileInfoExchangeCenter {

    private static final Logger logger = LogManager.getLogger(FileInfoExchangeCenter.class);

    @Value("${bathInsertSize}")
    private int bathInsertSize;

    private final File[] files = File.listRoots();

    private int syncFinishedFileNumber = 0;

    private final WriteFileSizeTask writeFileSizeTask;
    private final ReadFileTask readFileTask;
    private final TaskFinishedTask taskFinishedTask;

    private final ThreadLocal<List<FileSize>> threadFileSizeList = new FileSizeListThreadLocal();

    public FileInfoExchangeCenter(WriteFileSizeTask writeFileSizeTask, ReadFileTask readFileTask, TaskFinishedTask taskFinishedTask) {
        this.writeFileSizeTask = writeFileSizeTask;
        this.readFileTask = readFileTask;
        this.taskFinishedTask = taskFinishedTask;
    }

    public void start() {
        logger.info("start read file...");
        for (File file : files) {
            readFileTask.read(file, this);
        }
        logger.info("end read file...");
    }

    /**
     * 添加文件大小信息
     *
     * @param fileSize 文件大小信息实体
     */
    void addFileSize(FileSize fileSize) {
        logger.debug(String.format("file size : %s ", fileSize));
        if (fileSize == FileSize.END) {
            writeFileSizeTask.writeFileSize(threadFileSizeList.get());
            threadFileSizeList.set(new ArrayList<>(bathInsertSize));
        } else {
            if (threadFileSizeList.get().size() == bathInsertSize) {
                writeFileSizeTask.writeFileSize(threadFileSizeList.get());
                threadFileSizeList.set(new ArrayList<>(bathInsertSize));
            }
            threadFileSizeList.get().add(fileSize);
        }

        if (fileSize == FileSize.END && ++syncFinishedFileNumber == files.length) {
            readFileTask.whenComplete();
            writeFileSizeTask.whenComplete(taskFinishedTask::start);
            finishTask();
        }
    }

    class FileSizeListThreadLocal extends ThreadLocal<List<FileSize>> {
        @Override
        protected List<FileSize> initialValue() {
            return new ArrayList<>(bathInsertSize);
        }
    }

    private void finishTask() {
        shutdown(readFileTask);
        shutdown(writeFileSizeTask);
    }

    private void shutdown(ShuntDownable shuntDownAble) {
        shuntDownAble.shutDown();
    }
}
