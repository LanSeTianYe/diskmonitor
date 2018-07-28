package com.sun.xiaotian.diskmonitor.model;


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
    private int fileCountId;
    private int totalCount;
    private int fileCount;
    private int directoryCount;
    private int unKnowFileCount;
    private Date recordDate;


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
