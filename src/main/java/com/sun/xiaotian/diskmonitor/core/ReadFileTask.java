package com.sun.xiaotian.diskmonitor.core;

import com.sun.xiaotian.diskmonitor.cache.OneClassOneCache;
import com.sun.xiaotian.diskmonitor.factory.DMThreadFactory;
import com.sun.xiaotian.diskmonitor.model.FileRecordDate;
import com.sun.xiaotian.diskmonitor.model.FileSize;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 读取文件信息任务
 */

@Component
public class ReadFileTask implements ShuntDownable {

    private static final Logger logger = LogManager.getLogger(ReadFileTask.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), DMThreadFactory.getInstance());

    private FileInfoExchangeCenter fileInfoExchangeCenter;

    private FileRecordDate recordDate;

    private final OneClassOneCache oneClassOneCache;

    public ReadFileTask(OneClassOneCache oneClassOneCache) {
        this.oneClassOneCache = oneClassOneCache;
    }

    void read(File file, FileInfoExchangeCenter fileInfoExchangeCenter) {
        recordDate = oneClassOneCache.get(FileRecordDate.class);
        this.fileInfoExchangeCenter = fileInfoExchangeCenter;
        CompletableFuture
                .runAsync(() -> {
                    try {
                        logger.info("read file start ..." + file.getAbsolutePath());
                        readAllFileSizeInfo(file);
                        logger.info("read file success ..." + file.getAbsolutePath());
                        fileInfoExchangeCenter.addFileSize(FileSize.END);
                    } catch (Exception e) {
                        logger.error("read file exception" + file.getAbsolutePath(), e);
                    }
                }, executorService);
    }

    /**
     * 遍历读取文件信息
     *
     * @param file 要读取的文件或目录
     * @return 文件大小
     */
    private long readAllFileSizeInfo(File file) {
        if (null == file) {
            return 0;
        }
        if (file.isDirectory()) {
            long fileSize = 0;
            File[] files = file.listFiles();
            if (null != files) {
                fileSize = Arrays.stream(files).mapToLong(this::readAllFileSizeInfo).sum();
            }
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
        fileSize.setRecordDate(recordDate.getRecordDate());
        return fileSize;
    }

    @Override
    public void shutDown() {
        this.executorService.shutdown();
    }
}
