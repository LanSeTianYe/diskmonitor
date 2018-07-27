package com.sun.xiaotian.diskmonitor.service.impl;

import com.sun.xiaotian.diskmonitor.mapper.FileSizeMapper;
import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.service.FileSizeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileSizeServiceImpl implements FileSizeService {

    private final FileSizeMapper fileSizeMapper;

    public FileSizeServiceImpl(FileSizeMapper fileSizeMapper) {
        this.fileSizeMapper = fileSizeMapper;
    }

    @Override
    public int batchInsert(List<FileSize> fileSizeList) {
        return fileSizeMapper.batchInsert(fileSizeList);
    }
}
