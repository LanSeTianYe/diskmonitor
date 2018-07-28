package com.sun.xiaotian.diskmonitor.core;


import com.sun.xiaotian.diskmonitor.factory.DMThreadFactory;
import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.service.FileSizeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;


/**
 * 写入文件信息到数据库
 */

@Component
public class WriteFileSizeTask implements ShuntDownable {

    private static final Logger logger = LogManager.getLogger(WriteFileSizeTask.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), DMThreadFactory.getInstance());

    private final FileSizeService fileSizeService;

    public WriteFileSizeTask(FileSizeService fileSizeService) {
        this.fileSizeService = fileSizeService;
    }

    void writeFileSize(List<FileSize> fileSizeList) {
        CompletableFuture
                .runAsync(() -> fileSizeService.saveAll(fileSizeList), executorService)
                .exceptionally(
                        throwable -> {
                            if(throwable == null) {
                                return null;
                            }
                            logger.error(throwable.getMessage(), throwable);
                            return null;
                        }
                );
    }

    @Override
    public void shutDown() {
        this.executorService.shutdown();
    }
}
