package com.sun.xiaotian.diskmonitor.core;


import com.alibaba.fastjson.JSON;
import com.sun.xiaotian.diskmonitor.factory.DMThreadFactory;
import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.service.FileSizeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 写入文件信息到数据库
 */

@Component
public class WriteFileSizeTask implements ShuntDownable {

    private static final Logger logger = LogManager.getLogger(WriteFileSizeTask.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), DMThreadFactory.getInstance());

    private final List<CompletableFuture<Void>> completableFutureList = new ArrayList<>();

    private final FileSizeService fileSizeService;

    public WriteFileSizeTask(FileSizeService fileSizeService) {
        this.fileSizeService = fileSizeService;
    }

    void writeFileSize(List<FileSize> fileSizeList) {
        CompletableFuture<Void> completableFuture = CompletableFuture
                .runAsync(() -> fileSizeService.saveAll(fileSizeList), executorService)
                .exceptionally(
                        throwable -> {
                            if (throwable != null) {
                                logger.error(JSON.toJSON(fileSizeList));
                                logger.error(throwable.getMessage(), throwable);
                            }
                            return null;
                        }
                ).whenComplete((v,t) -> logger.info("write file size success ..."));
        completableFutureList.add(completableFuture);
    }

    @Override
    public void shutDown() {
        this.executorService.shutdown();
    }

    void whenComplete() {
        try {
            CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0])).get();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("all writeFileSizeTask finished ...");
    }
}
