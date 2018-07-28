package com.sun.xiaotian.diskmonitor.service.impl;

import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.repository.FileSizeRepository;
import com.sun.xiaotian.diskmonitor.service.FileSizeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileSizeServiceImpl implements FileSizeService {

    private final FileSizeRepository fileSizeRepositorye;

    public FileSizeServiceImpl(FileSizeRepository fileSizeRepositorye) {
        this.fileSizeRepositorye = fileSizeRepositorye;
    }

    @Override
    public void saveAll(List<FileSize> fileSizeList) {
        fileSizeRepositorye.saveAll(fileSizeList);
    }
}
