package com.sun.xiaotian.diskmonitor.core;

import com.sun.xiaotian.diskmonitor.cache.OneClassOneCache;
import com.sun.xiaotian.diskmonitor.model.FileCount;
import com.sun.xiaotian.diskmonitor.model.FileRecordDate;
import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.service.FileRecordDateService;
import com.sun.xiaotian.diskmonitor.service.FileSizeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
    private final FileRecordDateService fileRecordDateService;
    private final OneClassOneCache oneClassOneCache;
    private final FileSizeService fileSizeService;

    private final ThreadLocal<List<FileSize>> threadFileSizeList = new FileSizeListThreadLocal();

    public FileInfoExchangeCenter(WriteFileSizeTask writeFileSizeTask, ReadFileTask readFileTask, FileRecordDateService fileRecordDateService, OneClassOneCache oneClassOneCache, FileSizeService fileSizeService) {
        this.writeFileSizeTask = writeFileSizeTask;
        this.readFileTask = readFileTask;
        this.fileRecordDateService = fileRecordDateService;
        this.oneClassOneCache = oneClassOneCache;
        this.fileSizeService = fileSizeService;
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
        if (threadFileSizeList.get().size() == bathInsertSize || fileSize == FileSize.END) {
            writeFileSizeTask.writeFileSize(threadFileSizeList.get());
            threadFileSizeList.set(new ArrayList<>(bathInsertSize));
        } else {
            threadFileSizeList.get().add(fileSize);
        }

        if (fileSize == FileSize.END && ++syncFinishedFileNumber == files.length) {
            updateRecordDate();
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

    private void updateRecordDate() {
        FileRecordDate recordDate = oneClassOneCache.get(FileRecordDate.class);
        recordDate.setEndDate(new Date());
        fileRecordDateService.saveOrUpdate(recordDate);
        long costTime = recordDate.getEndDate().getTime() - recordDate.getStartDate().getTime();
        logger.info(String.format("task run finished ! cost time: %s ms, record data: %s, ", costTime, recordDate));
        logger.info(String.format("file count : %s", oneClassOneCache.get(FileCount.class)));
        logger.info(String.format("add count: %s", fileSizeService.findRecordCount(recordDate.getRecordDate())));
    }
}
