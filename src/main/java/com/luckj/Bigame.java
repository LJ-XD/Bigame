package com.luckj;

import com.luckj.commands.RegisterUserCommand;
import com.luckj.config.BigameConfig;
import com.luckj.constants.BaseGameConstants;
import com.luckj.constants.BotOrderConstants;
import com.luckj.service.MessageService;
import com.luckj.service.impl.MessageServiceImpl;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.Group;
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
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * Bigame插件启动类
 *
 * @author lj
 * @date 2024/08/05
 */
public final class Bigame extends JavaPlugin {

    public static final Bigame INSTANCE = new Bigame();

    //    private static Map<Integer, Object> gameBeginMap=new HashMap<>();

    private static LocalDate checkInTime = null;
    private static LocalDateTime senderTime = LocalDateTime.now();

    private MessageService messageService;


    private Bigame() {
        super(new JvmPluginDescriptionBuilder("com.luckj.bigame", "0.1.0")
                .name("bigame")
                .author("LJ")
                .build());
        this.messageService = new MessageServiceImpl();
        this.miraiLogger = getLogger();
    }

    private final MiraiLogger miraiLogger;

    @Override
    public void onEnable() {
        BigameConfig config = BigameConfig.getInstance();
        CommandManager.INSTANCE.registerCommand(new RegisterUserCommand(), true);
        Path currentRelativePath = Paths.get("");
        Path currentAbsoluteDirectory = currentRelativePath.toAbsolutePath();
        miraiLogger.info("============" + currentAbsoluteDirectory + "=============");
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(INSTANCE);
        //获取群聊消息
        eventChannel.subscribeAlways(GroupMessageEvent.class, event -> {
            Bot bot = event.getBot();
            Group group = event.getGroup();
            Member sender = event.getSender();
            MessageChain chain = event.getMessage();
            String message = chain.contentToString();
            if (BotOrderConstants.REGISTER_USER.equals(message)) {
                messageService.registerUser(sender, event);
            } else if (message.startsWith(BaseGameConstants.BotConstants.NAME)) {
                messageService.question(message, event);
            } else if (message.startsWith(BaseGameConstants.BotConstants.REPEAT)) {
                messageService.repeat(message, event, sender);
            } else if (message.startsWith(BaseGameConstants.BotConstants.MO_YU)) {
                messageService.moYu(group, event);
            } else if (message.startsWith(BaseGameConstants.BotConstants.DRAW)) {
                messageService.generatePicture(event, group, message);
            }
        });
        eventChannel.subscribeAlways(FriendMessageEvent.class, event -> {
            //获取好友消息
            miraiLogger.info(event.getMessage().contentToString());
        });
    }
}