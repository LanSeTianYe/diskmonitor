package com.sun.xiaotian.diskmonitor.model;

import com.sun.xiaotian.diskmonitor.annotation.FiledMeaning;
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
    @FiledMeaning("文件大小ID")
    private Integer fileSizeId;

    @Column(length = 500)
    @FiledMeaning("文件绝对路径")
    private String fileAbsolutePath;

    @FiledMeaning("文件大小")
    private long fileSize;

    @FiledMeaning("记录日期")
    private Date recordDate;

    static class InnerClass {
        final static FileSize instance = new FileSize();
        static {
            instance.setRecordDate(new Date(0));
            instance.setFileAbsolutePath("");
            instance.setFileSize(0);
        }
    }
}
