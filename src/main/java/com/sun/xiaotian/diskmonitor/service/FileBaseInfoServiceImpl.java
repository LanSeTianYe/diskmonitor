package com.sun.xiaotian.diskmonitor.service;


import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import com.sun.xiaotian.diskmonitor.repository.FileBaseInfoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FileBaseInfoServiceImpl implements FileBaseInfoService {

    private final static Logger logger = LogManager.getLogger(FileBaseInfoServiceImpl.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(8);

    private final FileBaseInfoRepository fileBaseInfoRepository;

    public FileBaseInfoServiceImpl(FileBaseInfoRepository fileBaseInfoRepository) {
        this.fileBaseInfoRepository = fileBaseInfoRepository;
    }

    @Override
    public void batchInsert(List<FileBaseInfo> fileBaseInfoList) {
        fileBaseInfoRepository.saveAll(fileBaseInfoList);
    }

    @Override
    public void insert(FileBaseInfo fileBaseInfo) {
        executorService.submit(() -> fileBaseInfoRepository.save(fileBaseInfo));
    }
}
