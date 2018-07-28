package com.sun.xiaotian.diskmonitor.service.impl;

import com.sun.xiaotian.diskmonitor.model.FileRecordDate;
import com.sun.xiaotian.diskmonitor.repository.FileRecordDateRepository;
import com.sun.xiaotian.diskmonitor.service.FileRecordDateService;
import com.sun.xiaotian.diskmonitor.util.DateFormatUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FileRecordDateServiceImpl implements FileRecordDateService {

    private final FileRecordDateRepository fileRecordDateRepository;

    public FileRecordDateServiceImpl(FileRecordDateRepository fileRecordDateRepository) {
        this.fileRecordDateRepository = fileRecordDateRepository;
    }

    @Override
    public FileRecordDate addFileRecordDate() {
        FileRecordDate fileRecordDate = new FileRecordDate();
        Date currDate = new Date();
        fileRecordDate.setRecordDate(DateFormatUtil.format(currDate));
        fileRecordDate.setStartDate(currDate);
        return fileRecordDateRepository.save(fileRecordDate);
    }

    @Override
    public FileRecordDate saveOrUpdate(FileRecordDate fileRecordDate) {
        return fileRecordDateRepository.saveAndFlush(fileRecordDate);
    }
}
