package com.sun.xiaotian.diskmonitor.core;

import com.sun.xiaotian.diskmonitor.cache.OneClassOneCache;
import com.sun.xiaotian.diskmonitor.model.FileCount;
import com.sun.xiaotian.diskmonitor.model.FileRecordDate;
import com.sun.xiaotian.diskmonitor.service.FileRecordDateService;
import com.sun.xiaotian.diskmonitor.service.FileSizeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class TaskFinishedTask {

    private static final Logger logger = LogManager.getLogger(TaskFinishedTask.class);

    private final OneClassOneCache oneClassOneCache;
    private final FileRecordDateService fileRecordDateService;
    private final FileSizeService fileSizeService;

    public TaskFinishedTask(OneClassOneCache oneClassOneCache, FileRecordDateService fileRecordDateService, FileSizeService fileSizeService) {
        this.oneClassOneCache = oneClassOneCache;
        this.fileRecordDateService = fileRecordDateService;
        this.fileSizeService = fileSizeService;
    }

    void start() {
        FileRecordDate recordDate = oneClassOneCache.get(FileRecordDate.class);
        FileCount fileCount = getFileCount();
        recordDate.setEndDate(new Date());
        fileRecordDateService.saveOrUpdate(recordDate);
        long costTime = recordDate.getEndDate().getTime() - recordDate.getStartDate().getTime();
        logger.info(String.format("task run finished ! cost time: %s s, record data: %s, ", costTime / 1000, recordDate));
        logger.info(String.format("file count : %s", fileCount));
        logger.info(String.format("add count: %s", fileSizeService.findRecordCount(recordDate.getRecordDate())));
    }

    /**
     * 从缓存中获取同步文件数量
     *
     * @return  需要同步文件数量
     */
    private FileCount getFileCount() {
        FileCount fileCount = oneClassOneCache.get(FileCount.class);
        int retryTimes = 0;
        while (null == fileCount) {
            fileCount = oneClassOneCache.get(FileCount.class);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                logger.error(String.format("TaskFinishedRunner_run_invoke ..., error message : %s ", e.getMessage()), e);
            }
            logger.info(String.format("get file count retry times : %s", ++retryTimes));
        }
        return fileCount;
    }
}
