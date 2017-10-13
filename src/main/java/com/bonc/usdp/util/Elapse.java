package com.bonc.usdp.util;

/**
 * created on 2017/10/13
 *
 * @author liyanjun@bonc.com.cn
 */
public class Elapse {

    private long startTime;
    private long endTime;
    private boolean isEnd;

    public void start() {
        isEnd = false;
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        endTime = System.currentTimeMillis();
        isEnd = true;
    }

    public String get() {
        if (!isEnd) {
            stop();
        }
        long totalTime = endTime - startTime;
        long totalSec = totalTime / 1000;
        long sec = totalSec;
        long min = sec / 60;
        sec = sec % 60;
        long hour = min / 60;
        min = min % 60;
        return String.format("%sh %sm %ss (%ss)", hour, min, sec, totalSec);
    }

}
