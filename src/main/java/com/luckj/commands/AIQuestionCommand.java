package com.luckj.commands;

import com.luckj.Bigame;
import com.luckj.utils.WenXinAiUtil;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.java.JSimpleCommand;

/**
 * 测试聊天指令
 *
 * @author lj
 * @date 2024/08/06
 */
public final class AIQuestionCommand extends JSimpleCommand {


    public AIQuestionCommand() {
        super(Bigame.INSTANCE, "chat");
        setDescription("和米若聊天");
    }

    @Handler
    public void foo(CommandContext context, @Name("内容") String content) {
        System.out.println(WenXinAiUtil.questionByIam(content));
    }
}