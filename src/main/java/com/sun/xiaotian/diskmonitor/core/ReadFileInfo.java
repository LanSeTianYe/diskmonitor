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
import java.util.Arrays;
import java.util.Date;

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

    private final Date date = new Date();

    private final static Logger logger = LogManager.getLogger(ReadFileInfo.class);

    @Value("${filePathList}")
    private String filePathList;

    @Override
    public void run(String... args) throws Exception {
        Arrays.asList(filePathList.split(",")).forEach(System.out::println);
        Arrays.asList(filePathList.split(",")).stream().map(Paths::get).map(Path::toFile).forEach(this::getFileSie);
    }

    public long getFileSie(File file) {
        if(file.isDirectory()) {
            if (null == file.listFiles()) { return 0; }

            File[] files = file.listFiles();
            long fileSize = 0;
            for (int i = 0; i < files.length; i++) {
                fileSize = fileSize + getFileSie(files[i]);
            }
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
        fileBaseInfo.setFileId(file.getAbsolutePath());
        fileBaseInfo.setFilePath(file.getPath());
        fileBaseInfo.setDirectory(file.isDirectory());
        fileBaseInfo.setRecordDate(dateFormatUtil.format(date));
        fileBaseInfoRepository.save(fileBaseInfo);

        FileSize fileSize = new FileSize();
        fileSize.setFileBaseInfoId(file.getAbsolutePath());
        fileSize.setFileSize(size);
        fileSize.setRecordDate(dateFormatUtil.format(date));
        fileSizeRepository.save(fileSize);
    }

}
