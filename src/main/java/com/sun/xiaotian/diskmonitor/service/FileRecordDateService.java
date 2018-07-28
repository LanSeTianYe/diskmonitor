package com.sun.xiaotian.diskmonitor.service;


import com.sun.xiaotian.diskmonitor.model.FileRecordDate;


public interface FileRecordDateService {

    /**
     * 添加文件记录日期
     */
    FileRecordDate addFileRecordDate();


    /**
     * 新增或更新
     * @param fileRecordDate
     * @return
     */
    FileRecordDate saveOrUpdate(FileRecordDate fileRecordDate);
}
