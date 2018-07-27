package com.sun.xiaotian.diskmonitor.service;

import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FileBaseInfoServiceTest {

    @Autowired
    private FileBaseInfoService fileBaseInfoService;

    @Test
    public void batchInsert() {
        Date date = new Date();

        List<FileBaseInfo> fileBaseInfoList = new ArrayList<>();

        FileBaseInfo fileBaseInfo = new FileBaseInfo();
        fileBaseInfo.setFileAbsolutePath("11");
        fileBaseInfo.setFileName("1");
        fileBaseInfo.setFileType("1");
        fileBaseInfo.setFilePath("1");
        fileBaseInfo.setDirectory(false);
        fileBaseInfo.setRecordDate(date);
        fileBaseInfoList.add(fileBaseInfo);

        fileBaseInfo = new FileBaseInfo();
        fileBaseInfo.setFileAbsolutePath("22");
        fileBaseInfo.setFileName("2");
        fileBaseInfo.setFilePath("2");
        fileBaseInfo.setFileType("2");
        fileBaseInfo.setDirectory(false);
        fileBaseInfo.setRecordDate(date);
        fileBaseInfoList.add(fileBaseInfo);

        fileBaseInfo = new FileBaseInfo();
        fileBaseInfo.setFileAbsolutePath("33");
        fileBaseInfo.setFileName("3");
        fileBaseInfo.setFileType("3");
        fileBaseInfo.setFilePath("3");
        fileBaseInfo.setDirectory(false);
        fileBaseInfo.setRecordDate(date);
        fileBaseInfoList.add(fileBaseInfo);
        fileBaseInfoService.batchInsert(fileBaseInfoList);
        Assert.assertNotNull(fileBaseInfo.getFileBaseInfoId());
    }
}