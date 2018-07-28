package com.sun.xiaotian.diskmonitor.service;

import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;

import java.util.List;

public interface FileBaseInfoService {

    void saveAll(List<FileBaseInfo> fileBaseInfoList);
}
