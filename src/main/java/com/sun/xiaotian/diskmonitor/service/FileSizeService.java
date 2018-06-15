package com.sun.xiaotian.diskmonitor.service;

import com.sun.xiaotian.diskmonitor.model.FileSize;

import java.util.List;

public interface FileSizeService {

    void batchInsert(List<FileSize> fileSizeList);

    void insert(FileSize fileSize);
}
