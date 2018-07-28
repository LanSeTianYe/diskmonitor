package com.sun.xiaotian.diskmonitor.service;

import com.sun.xiaotian.diskmonitor.model.FileCount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FileCountServiceTest {

    @Autowired
    private FileCountService fileCountService;


    @Test
    public void testAddAndDelete() {
        FileCount fileCount = new FileCount();
        fileCount.setFileCount(100);
        fileCount.setDirectoryCount(200);
        fileCount = fileCountService.addOrUpdate(fileCount);
        assertNotNull(fileCount);
        fileCountService.delete(fileCount);
    }

}