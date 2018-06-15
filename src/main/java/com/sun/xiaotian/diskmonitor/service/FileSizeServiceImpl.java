package com.sun.xiaotian.diskmonitor.service;

import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.repository.FileSizeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class FileSizeServiceImpl implements FileSizeService {

    private final FileSizeRepository fileSizeRepository;

    private final ExecutorService executorService = Executors.newFixedThreadPool(8);

    public FileSizeServiceImpl(FileSizeRepository fileSizeRepository) {
        this.fileSizeRepository = fileSizeRepository;
    }

    @Override
    public void batchInsert(List<FileSize> fileSizeList) {
        fileSizeRepository.saveAll(fileSizeList);
    }

    @Override
    public void insert(FileSize fileSize) {
        executorService.submit(() -> fileSizeRepository.save(fileSize));
    }
}
