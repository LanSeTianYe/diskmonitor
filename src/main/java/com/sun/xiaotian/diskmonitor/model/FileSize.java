package com.sun.xiaotian.diskmonitor.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class FileSize implements Serializable, Cloneable {

    private String fileBaseInfoId;  //文件ID
    private Date recordDate;        //记录日期
    private long fileSize;          //文件大小

    @Override
    public FileSize clone() throws CloneNotSupportedException {
        return ((FileSize) super.clone());
    }
}
