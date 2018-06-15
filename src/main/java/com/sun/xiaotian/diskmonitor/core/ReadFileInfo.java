package com.sun.xiaotian.diskmonitor.core;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.service.FileBaseInfoService;
import com.sun.xiaotian.diskmonitor.service.FileSizeService;
import com.sun.xiaotian.diskmonitor.util.DateFormatUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 读取文件信息
 */
@Component
public class ReadFileInfo implements CommandLineRunner {

    private final Disruptor<FileBaseInfo> fileBaseInfoDisruptor = new Disruptor<>(new FileBaseInfoFactory(), 1 << 15, new ConsumeThreadFactory(), ProducerType.MULTI, new BlockingWaitStrategy());
    private final Disruptor<FileSize> fileSizeDisruptor = new Disruptor<>(new FileSizeFactory(), 1 << 15, new ConsumeThreadFactory(), ProducerType.MULTI, new BlockingWaitStrategy());

    private RingBuffer<FileBaseInfo> fileBaseInfoRingBuffer;
    private RingBuffer<FileSize> fileSizeRingBuffer;

    private final Date date;

    private final AtomicInteger insertCount = new AtomicInteger(0);

    private final static Logger logger = LogManager.getLogger(ReadFileInfo.class);

    @Value("${filePathList}")
    private String filePathList;

    @Value("${bathInsertSize}")
    private int bathInsertSize;

    private final FileBaseInfoService fileBaseInfoService;
    private final FileSizeService fileSizeService;

    @Autowired
    public ReadFileInfo(FileBaseInfoService fileBaseInfoService, FileSizeService fileSizeService, DateFormatUtil dateFormatUtil) {
        this.fileBaseInfoService = fileBaseInfoService;
        this.fileSizeService = fileSizeService;
        date = dateFormatUtil.format(new Date());
        //

        fileBaseInfoDisruptor.handleEventsWith(new EventHandler<FileBaseInfo>() {
            @Override
            public void onEvent(FileBaseInfo event, long sequence, boolean endOfBatch) throws Exception {
                fileBaseInfoService.insert(event);
            }
        });

        fileSizeDisruptor.handleEventsWith(new EventHandler<FileSize>() {
            @Override
            public void onEvent(FileSize event, long sequence, boolean endOfBatch) throws Exception {
                fileSizeService.insert(event);
            }
        });

        fileSizeDisruptor.start();
        fileBaseInfoDisruptor.start();

        fileBaseInfoRingBuffer = fileBaseInfoDisruptor.getRingBuffer();
        fileSizeRingBuffer = fileSizeDisruptor.getRingBuffer();
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.asList(filePathList.split(",")).forEach(logger::info);
        Arrays.stream(filePathList.split(",")).map(Paths::get).map(Path::toFile).forEach(this::getFileSie);
    }

    public long getFileSie(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null == files) {
                return 0;
            }
            long fileSize = Arrays.stream(files).mapToLong(this::getFileSie).sum();
            logger.info(String.format("file: %s, count: %s", file.getAbsolutePath(), insertCount.getAndIncrement()));
            addFileInfo(file, fileSize);
            return fileSize;
        } else {
            addFileInfo(file, file.length());
            return file.length();
        }
    }

    private void addFileInfo(File file, long size) {

        fileBaseInfoRingBuffer.publishEvent(new EventTranslator<FileBaseInfo>() {
            @Override
            public void translateTo(FileBaseInfo fileBaseInfo, long sequence) {
                fileBaseInfo.setFileName(file.getName());
                fileBaseInfo.setFileId(file.getAbsolutePath());
                fileBaseInfo.setFilePath(file.getPath());
                fileBaseInfo.setDirectory(file.isDirectory());
                fileBaseInfo.setRecordDate(date);
            }
        });

        fileSizeRingBuffer.publishEvent(new EventTranslator<FileSize>() {
            @Override
            public void translateTo(FileSize fileSize, long sequence) {
                fileSize.setFileBaseInfoId(file.getAbsolutePath());
                fileSize.setFileSize(size);
                fileSize.setRecordDate(date);
            }
        });
    }
}
