package com.sun.xiaotian.diskmonitor.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data

public class FileSize implements Serializable {
    private Long fileSizeId;        //文件大小ID
    private Long fileBaseInfoId;    //文件ID
    private long fileSize;          //文件大小
    private Date recordDate;        //记录日期
}
