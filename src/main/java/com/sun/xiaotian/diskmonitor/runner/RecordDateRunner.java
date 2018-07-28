package com.sun.xiaotian.diskmonitor.runner;

import com.sun.xiaotian.diskmonitor.cache.OneClassOneCache;
import com.sun.xiaotian.diskmonitor.model.FileRecordDate;
import com.sun.xiaotian.diskmonitor.service.FileRecordDateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class RecordDateRunner implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(RecordDateRunner.class);

    private final OneClassOneCache oneClassOneCache;
    private final FileRecordDateService fileRecordDateService;

    public RecordDateRunner(OneClassOneCache oneClassOneCache, FileRecordDateService fileRecordDateService) {
        this.oneClassOneCache = oneClassOneCache;
        this.fileRecordDateService = fileRecordDateService;
    }

    @Override
    public void run(String... args) {
        FileRecordDate recordDate = fileRecordDateService.addFileRecordDate();
        oneClassOneCache.add(recordDate);
        logger.info(String.format("aad recordDate: %s ", recordDate));
    }
}
