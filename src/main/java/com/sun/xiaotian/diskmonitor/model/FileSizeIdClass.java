package com.sun.xiaotian.diskmonitor.model;


import java.io.Serializable;
import java.util.Date;

public class FileSizeIdClass implements Serializable {

    private String fileBaseInfoId;

    private Date recordDate;

    public FileSizeIdClass() {
        
    }

    public FileSizeIdClass(String fileBaseInfoId, Date recordDate) {
        this.fileBaseInfoId = fileBaseInfoId;
        this.recordDate = recordDate;
    }

    public String getFileBaseInfoId() {
        return fileBaseInfoId;
    }

    public void setFileBaseInfoId(String fileBaseInfoId) {
        this.fileBaseInfoId = fileBaseInfoId;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileSizeIdClass that = (FileSizeIdClass) o;

        if (fileBaseInfoId != null ? !fileBaseInfoId.equals(that.fileBaseInfoId) : that.fileBaseInfoId != null)
            return false;
        return recordDate != null ? recordDate.equals(that.recordDate) : that.recordDate == null;
    }

    @Override
    public int hashCode() {
        int result = fileBaseInfoId != null ? fileBaseInfoId.hashCode() : 0;
        result = 31 * result + (recordDate != null ? recordDate.hashCode() : 0);
        return result;
    }
}
