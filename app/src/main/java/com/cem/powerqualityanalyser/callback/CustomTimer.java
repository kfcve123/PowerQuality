package com.cem.powerqualityanalyser.callback;

import com.cem.powerqualityanalyser.tool.log;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CustomTimer {
    private ScheduledExecutorService newScheduledThreadPool;
    private long initialDelay = 1000L;
    private long period = 1000L;
    private int timeCount = 0;
    private int timeMaxCount = 0;
    private CustomTimerCallback callback;
    private boolean running;
    private String timerName;

    public CustomTimer(long period) {
        this.initialDelay = period;
        this.period = period;
    }

    public CustomTimer(long period, int maxCount) {
        this.initialDelay = period;
        this.period = period;
        this.timeMaxCount = maxCount;
    }

    public CustomTimer(long initialDelay, long period, int maxCount) {
        this.initialDelay = initialDelay;
        this.period = period;
        this.timeMaxCount = maxCount;
    }

    public CustomTimer() {
    }

    public void setOnTimeCallback(CustomTimerCallback callback) {
        this.callback = callback;
    }

    public String getTimerName() {
        return this.timerName;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

    public void StartTimer() {
        if (running) {
            StopTimer();
        }

        if (newScheduledThreadPool == null || newScheduledThreadPool.isShutdown()) {
            newScheduledThreadPool = Executors.newScheduledThreadPool(1);
            TimerTask task1 = new TimerTask() {
                public void run() {
                    if(!timerPause){
                        timeCount = timeCount + 1;
                        boolean autoStop = false;
                        if (timeMaxCount != 0 && timeCount >= timeMaxCount) {
                            autoStop = true;
                            StopTimer();
                        }
                        if (callback != null) {
                            callback.OnTimeTick(timerName, timeCount * period, autoStop);
                        }
                    }

                }
            };
            this.timeCount = 0;
            this.running = true;
            this.newScheduledThreadPool.scheduleAtFixedRate(task1, this.initialDelay, this.period, TimeUnit.MILLISECONDS);
        }

    }


    /**
     * 暂停计时和数据传输
     */
    private boolean timerPause;
    public void setPause(boolean pause){
        this.timerPause = pause;
    }


    public void StartCustomTimer() {
        if (running) {
            StopTimer();
        }
        if (newScheduledThreadPool == null || newScheduledThreadPool.isShutdown()) {
            newScheduledThreadPool = Executors.newScheduledThreadPool(1);
            TimerTask task1 = new TimerTask() {
                public void run() {
                    if(!timerPause) {
                        timeCount = timeCount + 1;
                        boolean autoStop = false;
                        if (callback != null) {
                            callback.OnTimeTick(timerName, timeCount * period, autoStop);
                        }
                    }
                }
            };
            this.timeCount = 0;
            this.running = true;
            this.newScheduledThreadPool.scheduleAtFixedRate(task1, this.initialDelay, this.period, TimeUnit.MILLISECONDS);
        }

    }

    public long getPeriod() {
        return this.period;
    }

    public int getTimeMaxCount() {
        return this.timeMaxCount;
    }

    public void setTimeMaxCount(int timeMaxCount) {
        this.timeMaxCount = timeMaxCount;
    }

    public void StartTimer(int period) {
        this.period = (long)period;
        this.initialDelay = (long)period;
        this.StartTimer();
    }

    public void StopTimer() {
        this.running = false;
        if (this.newScheduledThreadPool != null) {
            this.newScheduledThreadPool.shutdown();
        }
        this.newScheduledThreadPool = null;
    }

    public boolean isRunning() {
        return this.running;
    }
}
