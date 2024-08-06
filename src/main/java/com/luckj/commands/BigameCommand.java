package com.luckj.commands;

import com.luckj.Bigame;
import net.mamoe.mirai.console.command.CommandSender;
import net.mamoe.mirai.console.command.java.JRawCommand;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;

/**
 * bigame 命令
 *
 * @author lj
 * @date 2024/08/06
 */
public final class BigameCommand extends JRawCommand {

    public static final BigameCommand INSTANCE = new BigameCommand();

    private BigameCommand() {
        super(Bigame.INSTANCE, "test");
        // 设置用法，这将会在 /help 中展示
        setUsage("/test");
        // 设置描述，也会在 /help 中展示
        setDescription("这是一个测试指令");
        // 设置指令前缀是可选的，即使用 `test` 也能执行指令而不需要 `/test`
        setPrefixOptional(true);
    }

    @Override
    public void onCommand(@NotNull CommandSender sender, @NotNull MessageChain args) {
        // 处理指令
    }
}
