package com.sun.xiaotian.diskmonitor.core;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileData implements Serializable {

    private String Id;

    private String parentId;

    private String filePath;

    private String fileName;

    private String fileType;

    private Long fileSize;

    private Date createDate;

    private Date lastModifyDate;

    private Date recordDate;

    @Override
    public String toString() {
        return "FileData{" + "Id='" + Id + '\'' + ", parentId='" + parentId + '\'' + ", filePath='" + filePath + '\'' + ", fileName='" + fileName + '\'' + ", fileSize=" + fileSize + ", createDate=" + createDate + ", lastModifyDate=" + lastModifyDate + ", recordDate=" + recordDate + '}';
    }

}
