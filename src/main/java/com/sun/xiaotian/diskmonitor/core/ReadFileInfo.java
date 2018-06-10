package com.sun.xiaotian.diskmonitor.core;

import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.repository.FileBaseInfoRepository;
import com.sun.xiaotian.diskmonitor.repository.FileSizeRepository;
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

    @Autowired
    private FileBaseInfoRepository fileBaseInfoRepository;

    @Autowired
    private FileSizeRepository fileSizeRepository;

    @Autowired
    private DateFormatUtil dateFormatUtil;

    private ExecutorService executorService = Executors.newFixedThreadPool(8);

    private final BlockingQueue<FileBaseInfo> fileBaseInfoQueue = new LinkedBlockingDeque<>(16000);

    private final BlockingQueue<FileSize> fileSizeQueue = new LinkedBlockingDeque<>(16000);

    private Date date;

    private final AtomicInteger insertCount = new AtomicInteger(0);

    private final static Logger logger = LogManager.getLogger(ReadFileInfo.class);

    @Value("${filePathList}")
    private String filePathList;

    @Override
    public void run(String... args) throws Exception {
        date = dateFormatUtil.format(new Date());
        consumer(8);
        Arrays.asList(filePathList.split(",")).forEach(logger::info);
        Arrays.stream(filePathList.split(",")).map(Paths::get).map(Path::toFile).forEach(this::getFileSie);
    }

    public long getFileSie(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null == files) {
                return 0;
            }
            long fileSize = Arrays.stream(files).mapToLong(this::getFileSie).count();
            addFileInfo(file, fileSize);
            return fileSize;
        } else {
            addFileInfo(file, file.length());
            return file.length();
        }
    }

    private void addFileInfo(File file, long size) {
        try {
            FileBaseInfo fileBaseInfo = new FileBaseInfo();
            fileBaseInfo.setFileName(file.getName());
            fileBaseInfo.setFileId(file.getAbsolutePath());
            fileBaseInfo.setFilePath(file.getPath());
            fileBaseInfo.setDirectory(file.isDirectory());
            fileBaseInfo.setRecordDate(date);
            fileBaseInfoQueue.put(fileBaseInfo);

            FileSize fileSize = new FileSize();
            fileSize.setFileBaseInfoId(file.getAbsolutePath());
            fileSize.setFileSize(size);
            fileSize.setRecordDate(date);
            fileSizeQueue.put(fileSize);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void consumer(int count) {
        for (int i = 0; i < count; i++) {
            executorService.submit(() -> {
                List<FileSize> fileSizeList = new ArrayList<>(2000);
                List<FileBaseInfo> fileBaseInfoList = new ArrayList<>(2000);
                while (true) {
                    for (int j = 0; j < 2000; j++) {
                        FileBaseInfo fileBaseInfo = fileBaseInfoQueue.poll();
                        FileSize fileSize = fileSizeQueue.poll();
                        if (null != fileSize) {
                            fileSizeList.add(fileSize);
                        }
                        if (null != fileBaseInfo) {
                            fileBaseInfoList.add(fileBaseInfo);
                        }
                    }
                    fileSizeRepository.saveAll(fileSizeList);
                    fileBaseInfoRepository.saveAll(fileBaseInfoList);
                    logger.info(insertCount.addAndGet(fileSizeList.size()));
                    fileSizeList.clear();
                    fileBaseInfoList.clear();
                }
            });
        }
    }
}
