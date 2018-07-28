package com.sun.xiaotian.diskmonitor.service;


import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface FileRecordDateService {

    /**
     * 添加文件记录日期
     *
     * @param recordDate 文件记录日期
     */
    void addFileRecordDate(Date recordDate);
}
