package com.sun.xiaotian.diskmonitor.repository;


import com.sun.xiaotian.diskmonitor.model.FileSize;
import com.sun.xiaotian.diskmonitor.model.FileSizeIdClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FileSizeRepository  extends JpaRepository<FileSize, FileSizeIdClass>, JpaSpecificationExecutor<FileSize> {

}
