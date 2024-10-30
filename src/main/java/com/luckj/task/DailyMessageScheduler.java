package com.luckj.task;

import com.baidubce.qianfan.core.builder.ChatBuilder;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DailyMessageScheduler {

    private static class SingletonHolder {
        private static final DailyMessageScheduler INSTANCE = new DailyMessageScheduler();
    }

    public static DailyMessageScheduler getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private final ScheduledExecutorService executor;
    public final LinkedHashMap<Integer, ScheduledFuture<?>> scheduledTaskMap;

    private DailyMessageScheduler() {
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.scheduledTaskMap = new LinkedHashMap<>();
    }

    /**
     * 每日发送
     *
     * @param sender  消息接收者
     * @param message 消息
     * @param delay   时间
     */
    public void sendDaily(CommandSender sender, String message, LocalTime delay) {
        Runnable task = () -> {
            sender.sendMessage(message);
            System.out.println("开始定时任务");
        };
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.of(date, delay);
        LocalDateTime now = LocalDateTime.now();
        long millis;
        // 比较当前时间与目标时间
        if (now.isAfter(dateTime)) {
            // 如果目标时间已经过去，计算到明天同一时间的毫秒值
            LocalDateTime tomorrow = dateTime.plusDays(1);
            Duration duration = Duration.between(now, tomorrow);
            millis = duration.toMillis();
            System.out.println("到明天: " + tomorrow + " 的毫秒值: " + millis);
        } else {
            // 如果目标时间未过去，计算到今天同一时间的毫秒值
            Duration duration = Duration.between(now, dateTime);
            millis = duration.toMillis();
            System.out.println("到今天: " + dateTime + " 的毫秒值: " + millis);
        }
        // 安排任务
        ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(task, millis, 1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        Integer size = scheduledTaskMap.size();
        String a = size + "," + delay + "=>" + message;
        scheduledTaskMap.put(size, scheduledFuture);
        System.out.println("定时任务已成功启动，scheduledTask: " + scheduledFuture);
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));
    }


    /**
     * 停止定时任务
     */
    public void stopTask(int... index) {
        for (int i : index) {
            ScheduledFuture<?> scheduledFuture = scheduledTaskMap.get(i);
            if (scheduledFuture != null) {
                boolean cancelled = scheduledFuture.cancel(false);
                if (cancelled) {
                    System.out.println("定时任务已成功取消");
                } else {
                    System.out.println("定时任务取消失败，当前状态: " + scheduledFuture.isCancelled());
                }
            } else {
                System.out.println("定时任务未启动或已被取消");
            }

            // 尝试优雅地关闭 executor
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow(); // 如果5秒内没有停止，再次尝试
                }
            } catch (InterruptedException e) {
                executor.shutdownNow(); // 中断异常时，再次尝试停止
                Thread.currentThread().interrupt(); // 恢复中断状态
            }
        }
    }

    public void close(GroupMessageEvent event, Long senderId, Map<Long, ChatBuilder> qaMap) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // 执行你需要的任务
                if (qaMap.get(senderId) != null) {
                    qaMap.remove(senderId);
                    event.getSubject().sendMessage("对话已关闭");
                }
                // 任务完成后取消计时器
                timer.cancel();
            }
        };
        long delay = 10 * 60 * 1000; // 10分钟
        timer.schedule(task, delay);
    }
}