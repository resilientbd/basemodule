
package com.faisal.basemodule.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class StopWatch {
    private Timer timer ;
    private boolean stopAble = false;
    private OnUpdateTimer onUpdateTimer;

    public StopWatch(OnUpdateTimer onUpdateTimer) {
        this.onUpdateTimer = onUpdateTimer;
        timer = new Timer();
    }

    public void startTimer() {
      //  timer = new Timer();
        long startTime = System.currentTimeMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (stopAble) {
                    timer.cancel();
                } else {
                    if (onUpdateTimer != null) {
                        onUpdateTimer.onUpdate(updateTime(startTime));
                    }
                }
            }
        }, 0,10);
    }

    public void startTimer(String timePattern) {
    //    timer = new Timer();
        long startTime = System.currentTimeMillis();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (stopAble) {
                    timer.cancel();
                } else {
                    if (onUpdateTimer != null) {
                        onUpdateTimer.onUpdate(updateTime(startTime,timePattern));
                    }
                }
            }
        }, 0,10);
    }

    public void stopTimer()
    {
        stopAble = true;
    }

    public String updateTime(long startTime) {
        long nowTime = System.currentTimeMillis();
        long cast = nowTime - startTime;
        Date date = new Date(cast);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss:S");
        return simpleDateFormat.format(date);
    }

    public String updateTime(long startTime,String pattern) {
        long nowTime = System.currentTimeMillis();
        long cast = nowTime - startTime;
        Date date = new Date(cast);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public interface OnUpdateTimer {
        void onUpdate(String time);
    }

}
