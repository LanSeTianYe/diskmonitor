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

    public final static FileSize END = InnerClass.instance;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer fileSizeId;        //文件大小ID
    @Column(length = 500)
    private String fileAbsolutePath;   //文件绝对路径
    private long fileSize;          //文件大小
    private Date recordDate;        //记录日期

    static class InnerClass {
        final static FileSize instance = new FileSize();
        static {
            instance.setRecordDate(new Date(0));
            instance.setFileAbsolutePath("");
            instance.setFileSize(0);
        }
    }
}
