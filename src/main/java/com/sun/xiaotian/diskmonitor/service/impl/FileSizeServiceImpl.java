package com.sun.xiaotian.diskmonitor.service.impl;

import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.repository.FileSizeRepository;
import com.sun.xiaotian.diskmonitor.service.FileSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FileSizeServiceImpl implements FileSizeService {

    @Autowired
    private FileSizeRepository fileSizeRepository;

    @Override
    public void saveAll(List<FileSize> fileSizeList) {
        fileSizeRepository.saveAll(fileSizeList);
    }

    @Override
    public int findRecordCount(Date recordDate) {
        return fileSizeRepository.countByRecordDateEquals(recordDate);
    }
}
