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
@Table(name = "dm_file_record_date")
public class FileRecordDate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @FiledMeaning("记录日期的ID")
    private Integer recordDateId;

    @FiledMeaning("记录日期")
    private Date recordDate;

    @FiledMeaning("开始同步时间")
    private Date startDate;

    @FiledMeaning("结束时间")
    private Date endDate;
}
