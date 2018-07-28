package com.sun.xiaotian.diskmonitor.repository;


import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FileBaseInfoRepository extends JpaRepository<FileBaseInfo, String>, JpaSpecificationExecutor<FileBaseInfo> {
}
