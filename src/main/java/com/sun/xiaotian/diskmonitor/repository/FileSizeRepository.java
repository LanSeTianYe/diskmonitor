package com.sun.xiaotian.diskmonitor.repository;


import com.sun.xiaotian.diskmonitor.model.FileSize;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface FileSizeRepository extends BaseRepository<FileSize, Integer> {

    int countByRecordDateEquals(Date recordDate);

}
