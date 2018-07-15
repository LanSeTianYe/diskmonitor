package com.sun.xiaotian.diskmonitor;


import java.io.File;
import java.nio.file.Files;

public class TestFile {

    public static void main(String[] args) {
        for (File file : File.listRoots()) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
