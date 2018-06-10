package com.sun.xiaotian.diskmonitor.core;


import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileTool {

    private static final List<FileBaseInfo> fileDataList = new ArrayList<>();

    public void getAllFile(Path path) throws IOException {
        addFile(path);
        if (path.toFile().isDirectory()) {
            File[] files = path.toFile().listFiles();
            if (null != files) {
                for (File file : files) {
                    getAllFile(file.toPath());
                }
            }

        }
    }

    public static void main(String[] args) throws IOException {
        FileTool fileTool = new FileTool();
        fileTool.getAllFile(Paths.get("c:\\"));
        fileDataList.forEach(System.out::println);
    }

    public void addFile(Path path) {
        File file = path.toFile();
        FileBaseInfo fileData = new FileBaseInfo();
        fileData.setFileName(file.getName());
        System.out.println(fileData);
        fileDataList.add(fileData);
    }
}
