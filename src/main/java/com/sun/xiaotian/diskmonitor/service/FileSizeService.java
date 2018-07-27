package com.sun.xiaotian.diskmonitor.service;

import com.sun.xiaotian.diskmonitor.model.FileSize;

import java.util.List;

public interface FileSizeService {

    public void batchInsert(List<FileSize> fileSizeList);
}
