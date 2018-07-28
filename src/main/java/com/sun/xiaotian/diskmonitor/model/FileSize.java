package com.sun.xiaotian.diskmonitor.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors
@Entity
@Table(name = "dm_file_size")
public class FileSize implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer fileSizeId;        //文件大小ID

    private String fileAbsolutePath;   //文件绝对路径

    private long fileSize;          //文件大小
    private Date recordDate;        //记录日期
}
