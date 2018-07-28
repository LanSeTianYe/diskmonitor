package com.sun.xiaotian.diskmonitor.service.impl;

import com.sun.xiaotian.diskmonitor.model.FileRecordDate;
import com.sun.xiaotian.diskmonitor.repository.FileRecordDateRepository;
import com.sun.xiaotian.diskmonitor.service.FileRecordDateService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FileRecordDateServiceImpl implements FileRecordDateService {

    private final FileRecordDateRepository fileRecordDateRepository;

    public FileRecordDateServiceImpl(FileRecordDateRepository fileRecordDateRepository) {
        this.fileRecordDateRepository = fileRecordDateRepository;
    }

    @Override
    public void addFileRecordDate(Date recordDate) {
        FileRecordDate fileRecordDate = new FileRecordDate();
        fileRecordDate.setRecordDate(recordDate);
        fileRecordDateRepository.save(fileRecordDate);
    }
}
