package com.netnote.javaclient.utils;

public class ThreadController {
    private volatile boolean stopFlag = false;

    public void stopThreads() {
        stopFlag = true;
    }

    public boolean isStopFlag() {
        return stopFlag;
    }
}
