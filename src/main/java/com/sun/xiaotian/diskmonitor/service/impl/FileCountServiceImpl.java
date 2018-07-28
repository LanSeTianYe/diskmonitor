package com.sun.xiaotian.diskmonitor.service.impl;

import com.sun.xiaotian.diskmonitor.model.FileCount;
import com.sun.xiaotian.diskmonitor.repository.FileCountRepository;
import com.sun.xiaotian.diskmonitor.service.FileCountService;
import org.springframework.stereotype.Service;

@Service
public class FileCountServiceImpl implements FileCountService {

    private final FileCountRepository fileCountRepository;

    public FileCountServiceImpl(FileCountRepository fileCountRepository) {
        this.fileCountRepository = fileCountRepository;
    }

    @Override
    public FileCount addOrUpdate(FileCount fileCount) {
        return fileCountRepository.saveAndFlush(fileCount);
    }

    @Override
    public void delete(FileCount fileCount) {
        fileCountRepository.delete(fileCount);
    }
}
