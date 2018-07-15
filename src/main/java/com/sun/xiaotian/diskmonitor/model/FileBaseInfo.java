package com.sun.xiaotian.diskmonitor.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileBaseInfo implements Serializable, Cloneable {

    private String fileId;          //文件Id， 路径 + 名字
    private String filePath;        //文件路径
    private String fileName;        //文件名字
    private String fileType;        //文件类型
    private boolean isDirectory;    //是否是目录
    private Date recordDate;        //记录日期

    @Override
    public FileBaseInfo clone() throws CloneNotSupportedException {
        return (FileBaseInfo) super.clone();
    }
}
