package com.sun.xiaotian.diskmonitor.service;

import com.sun.xiaotian.diskmonitor.model.FileSize;

import java.util.Date;
import java.util.List;

public interface FileSizeService {

    void saveAll(List<FileSize> fileSizeList);

    int findRecordCount(Date recordDate);
}
