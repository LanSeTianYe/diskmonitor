package com.sun.xiaotian.diskmonitor.repository;

import com.sun.xiaotian.diskmonitor.model.FileCount;
import org.springframework.stereotype.Repository;

@Repository
public interface FileCountRepository extends BaseRepository<FileCount, Integer> {
    
}
