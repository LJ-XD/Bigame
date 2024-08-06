package com.luckj.commands;

import com.luckj.Bigame;
import com.luckj.entity.User;
import com.luckj.service.UserService;
import com.luckj.service.impl.UserServiceImpl;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.java.JSimpleCommand;
import net.mamoe.mirai.utils.MiraiLogger;

/**
 * 简单指令
 *
 * @author lj
 * @date 2024/08/06
 */
public final class RegisterUserCommand extends JSimpleCommand {
    private final UserService userService;
    private static final MiraiLogger MIRAI_LOGGER = MiraiLogger.Factory.INSTANCE.create(RegisterUserCommand.class);


    public RegisterUserCommand() {
        super(Bigame.INSTANCE, "ru");
        this.userService = new UserServiceImpl();
    }

    @Handler
    public void foo(CommandContext context, String name, Long id) {
        userService.registerUser(new User(id, name));
    }
}