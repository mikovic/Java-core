package ru.geekbrains.classes.threads;

public class Timer {
    private long startTime = 0;
    private long stopTime = 0;
    private long interval = 0;
    private boolean flag = false;

    public Timer() {

    }

    public synchronized void start() {
        this.startTime = 0;
        this.flag = false;
        this.startTime = System.currentTimeMillis();
        this.flag = true;
    }

    public synchronized void stop() {
        if (flag) {
            this.stopTime = System.currentTimeMillis();
            this.interval = this.stopTime - this.startTime;
            flag = false;
        } else {
            System.out.println("Вы забыли первоначально включить таймер на start");
        }
    }

    public long getStopTime() {
        return stopTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getInterval() {
        return interval;
    }

    @Override
    public String toString() {
        return "Время выполнения: " +
                interval;

    }
}
