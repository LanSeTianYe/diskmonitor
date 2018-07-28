package com.sun.xiaotian.diskmonitor.model;

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
    private Integer recordDateId;   //记录日期的ID
    private Date recordDate;        //记录日期
    private Date startDate;         //开始同步时间
    private Date endDate;           //结束时间
}
