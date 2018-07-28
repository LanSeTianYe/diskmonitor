package com.sun.xiaotian.diskmonitor.util;

import com.sun.xiaotian.diskmonitor.model.FileCount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiskFileToolTest {

    private static final Logger logger = LogManager.getLogger(DiskFileToolTest.class);

    @Test
    public void getFileCount() {
        long start = System.currentTimeMillis();
        FileCount fileCount = DiskFileTool.getFileCount();
        logger.info(String.format("DiskFileTool.getFileCount  cost time : %s ms, fileCount: %s", ((System.currentTimeMillis() - start) / 1000), fileCount));
        assertEquals(fileCount.getFileCount() + fileCount.getDirectoryCount() + fileCount.getUnKnowFileCount(), fileCount.getTotalCount());
    }
}