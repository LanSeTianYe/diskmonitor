package com.sun.xiaotian.diskmonitor.core;

import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @author sunfeilong   (sunfl@cloud-young.com)
 * @version V1.0
 * @date 2018年06月10日 下午11:28
 */

public class FileTest {

    private final static Logger logger = LogManager.getLogger(FileTest.class);

    @Test
    public void testFilePathSize() {
        Path path = Paths.get("c:\\");
        logger.info(path.getFileName());
        logger.info(path.getFileSystem());
        logger.info(path.getParent());
        logger.info(path.getRoot());
        File file = path.toFile();
        logger.info("fileName: " + file.getName());
        logger.info("fileParent: " + file.getParent());
        logger.info("length: " + file.length());
        logger.info("getUsableSpace: " + file.getTotalSpace());
        logger.info("getUsableSpace: " + file.getUsableSpace());
        logger.info("getFreeSpace: " + file.getFreeSpace());
    }
}
