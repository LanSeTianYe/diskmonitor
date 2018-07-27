package com.sun.xiaotian.diskmonitor.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors
public class FileBaseInfo implements Serializable {
    private Long fileBaseInfoId;    //文件ID
    private String fileAbsolutePath;    //文件绝对路径
    private String filePath;        //文件路径
    private String fileName;        //文件名字
    private String fileType;        //文件类型
    private boolean isDirectory;    //是否是目录
    private Date recordDate;        //记录日期
}
