package com.sun.xiaotian.diskmonitor.runner;

import com.sun.xiaotian.diskmonitor.core.FileInfoExchangeCenter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(300)
public class ReadFileSizeRunner implements CommandLineRunner {

    private final FileInfoExchangeCenter fileInfoExchangeCenter;

    public ReadFileSizeRunner(FileInfoExchangeCenter fileInfoExchangeCenter) {
        this.fileInfoExchangeCenter = fileInfoExchangeCenter;
    }

    @Override
    public void run(String... args){
        fileInfoExchangeCenter.start();
    }
}
