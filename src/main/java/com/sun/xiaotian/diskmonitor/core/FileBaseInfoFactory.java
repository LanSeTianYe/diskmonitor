package com.sun.xiaotian.diskmonitor.core;

import com.lmax.disruptor.EventFactory;
import com.sun.xiaotian.diskmonitor.model.FileBaseInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileBaseInfoFactory implements EventFactory<FileBaseInfo> {

    private final static Logger logger = LogManager.getLogger(FileBaseInfoFactory.class);

    private final FileBaseInfo fileBaseInfo = new FileBaseInfo();

    @Override
    public FileBaseInfo newInstance() {
        try {
            return fileBaseInfo.clone();
        } catch (CloneNotSupportedException e) {
            logger.error(e.getMessage(), e);
        }
        return new FileBaseInfo();
    }
}
