package com.luckj.task;

import net.mamoe.mirai.contact.Group;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DailyMessageScheduler {

    public void send() {
        // 创建一个单线程的 ScheduledExecutorService
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
        };
        long delay = calculateDelayUntilNoon();
        executor.scheduleAtFixedRate(task, delay, 24 * 60 * 60, TimeUnit.SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));
    }


    private long calculateDelayUntilNoon() {
        long currentTimeMillis = System.currentTimeMillis();
        long noonTimeMillis = (currentTimeMillis / (24 * 60 * 60 * 1000) + 1) * (24 * 60 * 60 * 1000) + 12 * 60 * 60 * 1000;

        return noonTimeMillis - currentTimeMillis;
    }
}