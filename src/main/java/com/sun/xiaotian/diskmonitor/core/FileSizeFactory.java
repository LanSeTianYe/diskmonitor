package com.sun.xiaotian.diskmonitor.core;

import com.lmax.disruptor.EventFactory;
import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import com.sun.xiaotian.diskmonitor.model.FileSize;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class FileSizeFactory implements EventFactory<FileSize> {

    private final static Logger logger = LogManager.getLogger(FileSizeFactory.class);

    private final FileSize fileSize = new FileSize();

    @Override
    public FileSize newInstance() {
        try {
            return fileSize.clone();
        } catch (CloneNotSupportedException e) {
            logger.error(e.getMessage(), e);
        }
        return new FileSize();
    }
}
