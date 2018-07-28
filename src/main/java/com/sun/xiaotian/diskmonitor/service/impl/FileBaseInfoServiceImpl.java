package com.sun.xiaotian.diskmonitor.service.impl;

import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import com.sun.xiaotian.diskmonitor.repository.FileBaseInfoRepository;
import com.sun.xiaotian.diskmonitor.service.FileBaseInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileBaseInfoServiceImpl implements FileBaseInfoService {

    private final FileBaseInfoRepository fileBaseInfoRepository;

    public FileBaseInfoServiceImpl(FileBaseInfoRepository fileBaseInfoRepository) {
        this.fileBaseInfoRepository = fileBaseInfoRepository;
    }

    @Override
    public void saveAll(List<FileBaseInfo> fileBaseInfoList) {
        fileBaseInfoRepository.saveAll(fileBaseInfoList);
    }
}
