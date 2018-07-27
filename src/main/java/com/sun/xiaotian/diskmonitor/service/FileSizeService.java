package com.sun.xiaotian.diskmonitor.service;

import com.sun.xiaotian.diskmonitor.model.FileSize;

import java.util.List;

public interface FileSizeService {

    public int batchInsert(List<FileSize> fileSizeList);
}
