package com.sun.xiaotian.diskmonitor.service;


import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;

import java.util.List;

public interface FileBaseInfoService {

    void batchInsert(List<FileBaseInfo> fileBaseInfoList);

    void insert(FileBaseInfo fileBaseInfo);
}
