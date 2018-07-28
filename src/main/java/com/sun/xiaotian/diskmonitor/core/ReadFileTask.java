package com.sun.xiaotian.diskmonitor.core;

import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.util.DateFormatUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 读取文件信息任务
 */

@Component
public class ReadFileTask implements ShuntDownAble {

    private static final Logger logger = LogManager.getLogger(ReadFileTask.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setDaemon(false);
            return thread;
        }
    });

    private final Date date = DateFormatUtil.format(new Date());

    private FileInfoExchangeCenter fileInfoExchangeCenter;

    void read(File file, FileInfoExchangeCenter fileInfoExchangeCenter) {
        this.fileInfoExchangeCenter = fileInfoExchangeCenter;
        CompletableFuture
                .runAsync(() -> {
                    readAllFileSizeInfo(file);
                    fileInfoExchangeCenter.addFileSize(FileSize.END);
                }, executorService)
                .exceptionally(
                        throwable -> {
                            logger.error(throwable.getMessage(), throwable);
                            return null;
                        }
                ).thenAccept((v) -> {
                    logger.info("success ...");
                }
        );
    }

    /**
     * 遍历读取文件信息
     *
     * @param file 要读取的文件或目录
     * @return 文件大小
     */
    private long readAllFileSizeInfo(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null == files) {
                return 0;
            }
            long fileSize = Arrays.stream(files).mapToLong(this::readAllFileSizeInfo).sum();
            fileInfoExchangeCenter.addFileSize(initFileInfo(file, fileSize));
            return fileSize;
        } else {
            fileInfoExchangeCenter.addFileSize(initFileInfo(file, file.length()));
            return file.length();
        }
    }

    /**
     * 初始化文件大小信息实体
     *
     * @param file 文件
     * @param size 文件大小
     * @return FileSize
     */
    private FileSize initFileInfo(File file, long size) {
        FileSize fileSize = new FileSize();
        fileSize.setFileSize(size);
        fileSize.setFileAbsolutePath(file.getAbsolutePath());
        fileSize.setRecordDate(date);
        return fileSize;
    }

    @Override
    public void shutDown() {
        this.executorService.shutdown();
    }
}
