package com.sun.xiaotian.diskmonitor.core;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * 读取文件信息
 */
@Component
public class ReadFileInfo implements CommandLineRunner {

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
            long fileSize = 0;
            File[] files = file.listFiles();
            if (null == files) {
                return 0;
            }
            for (int i = 0; i < files.length; i++) {
                fileSize = fileSize + getFileSie(files[i]);
            }
            logger.info(String.format("fileName: %s, fileSize: %s kb", file.getAbsolutePath(), fileSize / 1024));
            return fileSize;
        } else {
            logger.info(String.format("fileName: %s, fileSize: %s kb", file.getAbsolutePath(), file.length() / 1024));
            return file.length();
        }
    }

    private void addFile() {

    }

    private void addFileSize() {

    }
}
