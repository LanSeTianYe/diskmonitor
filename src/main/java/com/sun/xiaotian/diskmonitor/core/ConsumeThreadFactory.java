package com.sun.xiaotian.diskmonitor.core;


import java.util.concurrent.ThreadFactory;

public class ConsumeThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
