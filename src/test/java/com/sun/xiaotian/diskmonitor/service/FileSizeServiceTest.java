package com.sun.xiaotian.diskmonitor.service;

import com.sun.xiaotian.diskmonitor.model.FileSize;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FileSizeServiceTest {

    @Autowired
    private FileSizeService fileSizeService;

    @Test
    public void batchInsert() {
        Date date = new Date();
        List<FileSize> fileSizeList = new ArrayList<>();
        FileSize first = new FileSize();
        first.setRecordDate(date);
        fileSizeList.add(first);
        first = new FileSize();
        first.setRecordDate(date);
        fileSizeList.add(first);
        first = new FileSize();
        first.setRecordDate(date);
        fileSizeList.add(first);
        fileSizeService.saveAll(fileSizeList);
        Assert.assertNotNull(first.getFileSizeId());
    }
}