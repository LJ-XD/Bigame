package com.luckj;

import com.luckj.commands.RegisterUserCommand;
import com.luckj.constants.BotOrderConstants;
import com.luckj.entity.User;
import com.luckj.service.UserService;
import com.luckj.service.impl.UserServiceImpl;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.utils.MiraiLogger;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Bigame插件启动类
 *
 * @author lj
 * @date 2024/08/05
 */
public final class Bigame extends JavaPlugin {

    public static final Bigame INSTANCE = new Bigame();

    private final UserService userService;
    public final MiraiLogger miraiLogger;


    private Bigame() {
        super(new JvmPluginDescriptionBuilder("com.luckj.bigame", "0.1.0")
                .name("bigame")
                .author("LJ")
                .build());
        this.userService = new UserServiceImpl();
        this.miraiLogger = getLogger();
    }

    @Override
    public void onEnable() {
        userService.welcomeUser();
        CommandManager.INSTANCE.registerCommand(new RegisterUserCommand(), true);
        Path currentRelativePath = Paths.get("");
        Path currentAbsoluteDirectory = currentRelativePath.toAbsolutePath();
        miraiLogger.info("============" + currentAbsoluteDirectory + "=============");
        // 创建监听
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(INSTANCE);
        eventChannel.subscribeAlways(GroupMessageEvent.class, event -> {
            // 可获取到消息内容等, 详细查阅 `GroupMessageEvent`
            MessageChain chain = event.getMessage();
            Member sender = event.getSender();
            String message = "";
            String s = chain.contentToString();
            miraiLogger.info(s);
            if (BotOrderConstants.REGISTER_USER.equals(s)) {
                User user = new User();
                user.setId(sender.getId());
                user.setName(sender.getNick());
                message = userService.registerUser(user);
                event.getSubject().sendMessage(message); // 回复消息
            }
        });
        eventChannel.subscribeAlways(FriendMessageEvent.class, event -> {
            // 监听好友消息
            miraiLogger.info(event.getMessage().contentToString());
        });
    }
}