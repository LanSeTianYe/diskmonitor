package com.sun.xiaotian.diskmonitor.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Closeable;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(FileSizeIdClass.class)
@Table(
        name = "file_size",
        uniqueConstraints = @UniqueConstraint(columnNames = {"file_base_info_id", "record_date"})
)
@Data
public class FileSize implements Serializable, Cloneable {

    @Id
    @Column(name = "file_base_info_id")
    private String fileBaseInfoId;  //文件ID
    @Id
    @Column(name = "record_date")
    private Date recordDate;        //记录日期

    @Column(name = "file_size")
    private long fileSize;          //文件大小

    @Override
    public FileSize clone() throws CloneNotSupportedException {
        return ((FileSize) super.clone());
    }
}
