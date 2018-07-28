package com.sun.xiaotian.diskmonitor.runner;

import com.sun.xiaotian.diskmonitor.cache.OneClassOneCache;
import com.sun.xiaotian.diskmonitor.model.FileCount;
import com.sun.xiaotian.diskmonitor.model.FileRecordDate;
import com.sun.xiaotian.diskmonitor.service.FileCountService;
import com.sun.xiaotian.diskmonitor.util.DiskFileTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Order(200)
public class InitFileCountRunner implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(InitFileCountRunner.class);

    private final FileCountService fileCountService;
    private final OneClassOneCache oneClassOneCache;

    public InitFileCountRunner(FileCountService fileCountService, OneClassOneCache oneClassOneCache) {
        this.fileCountService = fileCountService;
        this.oneClassOneCache = oneClassOneCache;
    }

    @Override
    public void run(String... args) {
        CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();
            FileCount fileCount = DiskFileTool.getFileCount();
            fileCount.setRecordDate(oneClassOneCache.get(FileRecordDate.class).getRecordDate());
            logger.info(String.format("init file count task finished ! cost time: %s ms, fileCount: %s", ((System.currentTimeMillis() - startTime) / 1000), fileCount));
            fileCount = fileCountService.addOrUpdate(fileCount);
            oneClassOneCache.add(fileCount);
        });
    }
}
