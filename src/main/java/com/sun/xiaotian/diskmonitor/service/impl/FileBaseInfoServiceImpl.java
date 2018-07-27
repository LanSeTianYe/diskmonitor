package com.sun.xiaotian.diskmonitor.service.impl;

import com.sun.xiaotian.diskmonitor.mapper.FileBaseInfoMapper;
import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import com.sun.xiaotian.diskmonitor.service.FileBaseInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileBaseInfoServiceImpl implements FileBaseInfoService {

    private final FileBaseInfoMapper fileBaseInfoMapper;

    public FileBaseInfoServiceImpl(FileBaseInfoMapper fileBaseInfoMapper) {
        this.fileBaseInfoMapper = fileBaseInfoMapper;
    }

    @Override
    public void batchInsert(List<FileBaseInfo> fileBaseInfoList) {
        fileBaseInfoMapper.batchInsert(fileBaseInfoList);
    }
}
