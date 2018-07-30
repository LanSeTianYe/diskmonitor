package com.sun.xiaotian.diskmonitor.model;


import com.sun.xiaotian.diskmonitor.annotation.FiledMeaning;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;


@Data
@Accessors
@Entity
@Table(name = "dm_file_count")
public class FileCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FiledMeaning("文件数量Id")
    private int fileCountId;

    @FiledMeaning("更新时的实际文件数量")
    private int totalCount;

    @FiledMeaning("文件数量")
    private int fileCount;

    @FiledMeaning("文件夹数量")
    private int directoryCount;

    @FiledMeaning("未知类型文件数量")
    private int unKnowFileCount;

    @FiledMeaning("COMMENT '记录日期")
    private Date recordDate;

    @FiledMeaning("实际同步文件数量")
    private int actualCount;

    public void addOneFile() {
        this.fileCount++;
        calculateFileCount();
    }

    public void addOneDirectory() {
        this.directoryCount++;
        calculateFileCount();
    }

    public void addUnKnowFileCount() {
        this.unKnowFileCount++;
        calculateFileCount();
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
        calculateFileCount();
    }

    public void setDirectoryCount(int directoryCount) {
        this.directoryCount = directoryCount;
        calculateFileCount();
    }

    public void setUnKnowFileCount(int unKnowFileCount) {
        this.unKnowFileCount = unKnowFileCount;
        calculateFileCount();
    }

    private void calculateFileCount() {
        this.totalCount = this.fileCount + this.directoryCount + this.unKnowFileCount;
    }
}
