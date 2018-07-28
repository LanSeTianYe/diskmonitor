package com.sun.xiaotian.diskmonitor.repository;


import com.sun.xiaotian.diskmonitor.model.FileRecordDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRecordDateRepository extends JpaRepository<FileRecordDate, Integer>, JpaSpecificationExecutor<FileRecordDate> {

}
