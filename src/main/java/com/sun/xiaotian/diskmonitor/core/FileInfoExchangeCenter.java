package com.sun.xiaotian.diskmonitor.core;

import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.service.FileRecordDateService;
import com.sun.xiaotian.diskmonitor.util.DateFormatUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 文件信息交换中心
 */

@Component
public class FileInfoExchangeCenter implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(FileInfoExchangeCenter.class);

    @Value("${bathInsertSize}")
    private int bathInsertSize;

    private final File[] files = File.listRoots();

    private int syncFinishedFileNumber = 0;

    private final FileRecordDateService fileRecordDateService;

    private final WriteFileSizeTask writeFileSizeTask;
    private final ReadFileTask readFileTask;

    private final Date date = DateFormatUtil.format(new Date());

    private final ThreadLocal<List<FileSize>> threadFileSizeList = new FileSizeListThreadLocal();

    public FileInfoExchangeCenter(FileRecordDateService fileRecordDateService, WriteFileSizeTask writeFileSizeTask, ReadFileTask readFileTask) {
        this.fileRecordDateService = fileRecordDateService;
        this.writeFileSizeTask = writeFileSizeTask;
        this.readFileTask = readFileTask;
    }

    @Override
    public void run(String... args) throws InterruptedException {
        fileRecordDateService.addFileRecordDate(date);
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
        if (threadFileSizeList.get().size() == bathInsertSize || fileSize == FileSize.END) {
            writeFileSizeTask.writeFileSize(threadFileSizeList.get());
            threadFileSizeList.set(new ArrayList<>(bathInsertSize));
        } else {
            threadFileSizeList.get().add(fileSize);
        }

        if (fileSize == FileSize.END && ++syncFinishedFileNumber == files.length) {
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

    private void shutdown(ShuntDownAble shuntDownAble) {
        shuntDownAble.shutDown();
    }

}
