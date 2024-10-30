package com.luckj.commands;

import com.luckj.Bigame;
import com.luckj.entity.User;
import com.luckj.service.UserService;
import com.luckj.service.impl.UserServiceImpl;
import com.luckj.utils.WenXinAiUtil;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import net.mamoe.mirai.utils.MiraiLogger;

/**
 * 测试聊天指令
 *
 * @author lj
 * @date 2024/08/06
 */
public final class AIQuestionCommand extends JSimpleCommand {
    private final UserService userService;
    private static final MiraiLogger MIRAI_LOGGER = MiraiLogger.Factory.INSTANCE.create(AIQuestionCommand.class);


    public AIQuestionCommand() {
        super(Bigame.INSTANCE, "chat");
        setDescription("和米若聊天");
        this.userService = new UserServiceImpl();
    }

    @Handler
    public void foo(CommandContext context, @Name("内容") String content) {
        System.out.println(WenXinAiUtil.questionByIam(content));
    }
}