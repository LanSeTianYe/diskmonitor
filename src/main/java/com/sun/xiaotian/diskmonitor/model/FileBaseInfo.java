package com.sun.xiaotian.diskmonitor.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "file_base_info")
public class FileBaseInfo implements Serializable {

    @Id
    @Column(name = "file_id")
    private String fileId;          //文件Id， 路径 + 名字
    @Column(name = "file_path")
    private String filePath;        //文件路径
    @Column(name = "file_name")
    private String fileName;        //文件名字
    @Column(name = "file_type")
    private String fileType;        //文件类型
    @Column(name = "is_directory")
    private boolean isDirectory;    //是否是目录
    @Column(name = "record_date")
    private Date recordDate;        //记录日期
}
