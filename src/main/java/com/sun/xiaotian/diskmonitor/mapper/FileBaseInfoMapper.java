package com.sun.xiaotian.diskmonitor.mapper;

import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileBaseInfoMapper {

    int deleteByPrimaryKey(Long fileBaseInfoId);

    int insert(FileBaseInfo record);

    int insertSelective(FileBaseInfo record);

    FileBaseInfo selectByPrimaryKey(Long fileBaseInfoId);

    List<String> selectByFileAbsolutePath(@Param("absolutePaths") List<String> absolutePaths);

    int updateByPrimaryKeySelective(FileBaseInfo record);

    int updateByPrimaryKey(FileBaseInfo record);

    void batchInsert(List<FileBaseInfo> fileBaseInfoList);
}
