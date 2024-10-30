package com.luckj.commands;

import com.luckj.Bigame;
import com.luckj.task.DailyMessageScheduler;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import net.mamoe.mirai.utils.MiraiLogger;

import java.time.LocalTime;

/**
 * 简单指令
 *
 * @author lj
 * @date 2024/08/06
 */
public final class DailyMessageSchedulerCommand extends JSimpleCommand {
    private static final MiraiLogger MIRAI_LOGGER = MiraiLogger.Factory.INSTANCE.create(DailyMessageSchedulerCommand.class);
    private final DailyMessageScheduler dailyMessageScheduler;

    public DailyMessageSchedulerCommand() {
        super(Bigame.INSTANCE, "sendDaily");
        this.dailyMessageScheduler = DailyMessageScheduler.getInstance();
        setDescription("开启每日消息定时任务");
    }

    @Handler
    public void foo(CommandContext context,
                    @Name("发送内容") String content,
                    @Name("时间，如：15:30:00 秒可省略") String time) {
        LocalTime localTime;
        try {
            localTime = LocalTime.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
            context.getSender().sendMessage("时间格式错误");
            return;
        }
        dailyMessageScheduler.sendDaily(context.getSender(), content, localTime);
        MIRAI_LOGGER.info("开启每日消息定时任务");
        context.getSender().sendMessage("开启每日" + localTime + "定时消息:" + content);
    }
}