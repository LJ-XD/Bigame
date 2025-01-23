package com.luckj.commands;

import com.luckj.Bigame;
import com.luckj.task.DailyMessageScheduler;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.java.JSimpleCommand;

/**
 * 关闭定时任务指令
 *
 * @author lj
 * @date 2024/08/06
 */
public final class StopTaskCommand extends JSimpleCommand {
    private final DailyMessageScheduler dailyMessageScheduler;

    public StopTaskCommand() {
        super(Bigame.INSTANCE, "stopTask");
        this.dailyMessageScheduler = DailyMessageScheduler.getInstance();
        setDescription("关闭定时任务");
    }

    @Handler
    public void foo(CommandContext context,@Name("任务序号") int index) {
        dailyMessageScheduler.stopTask(index);
        context.getSender().sendMessage("关闭每日全部定时任务");
    }
}