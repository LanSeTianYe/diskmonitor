package com.sun.xiaotian.diskmonitor.runner;

import com.sun.xiaotian.diskmonitor.cache.OneClassOneCache;
import com.sun.xiaotian.diskmonitor.model.FileCount;
import com.sun.xiaotian.diskmonitor.model.FileRecordDate;
import com.sun.xiaotian.diskmonitor.service.FileRecordDateService;
import com.sun.xiaotian.diskmonitor.service.FileSizeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Order(400)
public class TaskFinishedRunner implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(TaskFinishedRunner.class);

    private final OneClassOneCache oneClassOneCache;
    private final FileRecordDateService fileRecordDateService;
    private final FileSizeService fileSizeService;

    public TaskFinishedRunner(OneClassOneCache oneClassOneCache, FileRecordDateService fileRecordDateService, FileSizeService fileSizeService) {
        this.oneClassOneCache = oneClassOneCache;
        this.fileRecordDateService = fileRecordDateService;
        this.fileSizeService = fileSizeService;
    }

    @Override
    public void run(String... args){
        ApplicationShuntDownHook hookThread = new ApplicationShuntDownHook();
        hookThread.setDaemon(false);
        Runtime.getRuntime().addShutdownHook(hookThread);
    }

    private class ApplicationShuntDownHook extends Thread {
        @Override
        public void run() {
            FileRecordDate recordDate = oneClassOneCache.get(FileRecordDate.class);
            recordDate.setEndDate(new Date());
            fileRecordDateService.saveOrUpdate(recordDate);
            long costTime = recordDate.getEndDate().getTime() - recordDate.getStartDate().getTime();
            logger.info(String.format("task run finished ! cost time: %s s, record data: %s, ", costTime / 1000, recordDate));
            logger.info(String.format("file count : %s", oneClassOneCache.get(FileCount.class)));
            logger.info(String.format("add count: %s", fileSizeService.findRecordCount(recordDate.getRecordDate())));
        }
    }
}
