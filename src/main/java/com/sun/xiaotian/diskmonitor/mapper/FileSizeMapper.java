package com.sun.xiaotian.diskmonitor.mapper;


import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import com.sun.xiaotian.diskmonitor.model.FileSize;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileSizeMapper {

    int deleteByPrimaryKey(Long fileBaseInfoId);

    int insert(FileSize record);

    int insertSelective(FileSize record);

    FileBaseInfo selectByPrimaryKey(Long fileBaseInfoId);

    int updateByPrimaryKeySelective(FileSize record);

    int updateByPrimaryKey(FileSize record);

    int batchInsert(List<FileSize> fileSizeList);
}
