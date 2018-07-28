package com.sun.xiaotian.diskmonitor.service;

import com.sun.xiaotian.diskmonitor.model.FileCount;

public interface FileCountService {

    FileCount addOrUpdate(FileCount fileCount);

    void delete(FileCount fileCount);
}
