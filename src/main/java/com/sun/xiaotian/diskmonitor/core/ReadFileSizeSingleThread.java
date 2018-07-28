package com.sun.xiaotian.diskmonitor.core;

import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.util.DateFormatUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

/**
 * 读取文件信息
 */
@Component
public class ReadFileSizeSingleThread implements CommandLineRunner {

    private int fileCount = 100_0000;

    private FileSize[] fileSizeList = new FileSize[fileCount];
    private FileBaseInfo[] fileBaseInfoList = new FileBaseInfo[fileCount];


    private final Date date;

    private final static Logger logger = LogManager.getLogger(ReadFileSizeSingleThread.class);

    private int index = 0;

    private File[] files;

    @Value("${bathInsertSize}")
    private int bathInsertSize;


    public ReadFileSizeSingleThread() {
        date = DateFormatUtil.format(new Date());
        files = File.listRoots();
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.asList(files).forEach(logger::info);
        long start = System.currentTimeMillis();
//        Arrays.stream(files).forEach(this::getFileSie);
//        logger.info("costTime:" + (System.currentTimeMillis() - start));
//        logger.info(String.format("index: %s", index));
//        System.out.println();
    }

    private long getFileSie(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (null == files) {
                return 0;
            }
            long fileSize = Arrays.stream(files).mapToLong(this::getFileSie).sum();
            addFileInfo(file, fileSize);
            return fileSize;
        } else {
            addFileInfo(file, file.length());
            return file.length();
        }
    }

    private void addFileInfo(File file, long size) {
        FileBaseInfo fileBaseInfo = new FileBaseInfo();
        fileBaseInfo.setFileName(file.getName());
        fileBaseInfo.setFileName(file.getAbsolutePath());
        fileBaseInfo.setFilePath(file.getPath());
        fileBaseInfo.setDirectory(file.isDirectory());
        fileBaseInfo.setRecordDate(date);
        fileBaseInfoList[index] = fileBaseInfo;

        FileSize fileSize = new FileSize();
        fileSize.setFileSize(size);
        fileSize.setRecordDate(date);
        fileSizeList[index] = fileSize;
        index++;
    }
}
