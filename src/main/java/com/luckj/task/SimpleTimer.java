package com.luckj.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleTimer {
    private static final SimpleTimer INSTANCE = new SimpleTimer();
    private final ScheduledExecutorService executor;

    private SimpleTimer() {
        executor = Executors.newSingleThreadScheduledExecutor();
    }

    public static SimpleTimer getInstance() {
        return INSTANCE;
    }

    public void scheduleTask(Runnable task, long initialDelay, long period, TimeUnit timeUnit) {
        executor.scheduleAtFixedRate(task, initialDelay, period, timeUnit);
    }

    public void shutdown() {
        executor.shutdown();
    }

    public static void main(String[] args) {
        // 示例任务
        Runnable exampleTask = () -> System.out.println("Task executed at " + System.currentTimeMillis());

        // 获取定时任务实例
        SimpleTimer timer = SimpleTimer.getInstance();

        // 每隔 5 秒执行一次任务
        timer.scheduleTask(exampleTask, 0, 1, TimeUnit.DAYS);

        timer.shutdown();
    }
}
