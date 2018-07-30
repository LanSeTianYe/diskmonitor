package com.sun.xiaotian.diskmonitor.util;

import com.sun.xiaotian.diskmonitor.model.FileCount;

import java.io.File;

/**
 * 获取电脑文件数量
 */
public class DiskFileTool {

    private final static File[] files = File.listRoots();

    public static FileCount getFileCount() {
        FileCount fileCount = new FileCount();
        for (File file : files) {
            calculateFileCount(file, fileCount);
        }
        return fileCount;
    }

    /**
     * 遍历计算文件数量
     *
     * @param file      文件名字
     * @param fileCount 文件数量记录实体
     */
    private static void calculateFileCount(File file, FileCount fileCount) {
        if (null != file) {
            if (file.isFile()) {
                fileCount.addOneFile();
            } else if (file.isDirectory()) {
                fileCount.addOneDirectory();
                File[] files = file.listFiles();
                if (null != files) {
                    for (File childFile : files) {
                        calculateFileCount(childFile, fileCount);
                    }
                }
            } else {
                //非法文件
                fileCount.addUnKnowFileCount();
            }
        }
    }
}
