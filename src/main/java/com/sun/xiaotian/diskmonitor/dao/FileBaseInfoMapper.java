package com.sun.xiaotian.diskmonitor.dao;

import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface FileBaseInfoMapper {

    public void batchInsert(List<FileBaseInfo> fileBaseInfoList);
}
