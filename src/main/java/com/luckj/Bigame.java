package com.luckj;

import com.baidubce.qianfan.core.builder.ChatBuilder;
import com.luckj.commands.AIQuestionCommand;
import com.luckj.commands.DailyMessageSchedulerCommand;
import com.luckj.commands.RegisterUserCommand;
import com.luckj.commands.StopTaskCommand;
import com.luckj.constants.BotBaseConstants;
import com.luckj.constants.BotOrderConstants;
import com.luckj.service.MessageService;
import com.luckj.service.impl.MessageServiceImpl;
import com.luckj.task.DailyMessageScheduler;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Bigame插件启动类
 *
 * @author lj
 * @date 2024/08/05
 */
public final class Bigame extends JavaPlugin {

    public static final Bigame INSTANCE = new Bigame();
    private static Map<Long, ChatBuilder> qaMap = new HashMap<>();
    private final DailyMessageScheduler dailyMessageScheduler;


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
        this.dailyMessageScheduler = DailyMessageScheduler.getInstance();
    }

    private final MiraiLogger miraiLogger;

    @Override
    public void onEnable() {
        CommandManager.INSTANCE.registerCommand(new RegisterUserCommand(), true);
        CommandManager.INSTANCE.registerCommand(new AIQuestionCommand(), true);
        CommandManager.INSTANCE.registerCommand(new DailyMessageSchedulerCommand(), true);
        CommandManager.INSTANCE.registerCommand(new StopTaskCommand(), true);
        Path currentRelativePath = Paths.get("");
        Path currentAbsoluteDirectory = currentRelativePath.toAbsolutePath();
        miraiLogger.info("============" + currentAbsoluteDirectory + "=============");
        EventChannel<Event> eventChannel = GlobalEventChannel.INSTANCE.parentScope(INSTANCE);
        //获取群聊消息
        eventChannel.subscribeAlways(GroupMessageEvent.class, event -> {
            Group group = event.getGroup();
            Member sender = event.getSender();
            MessageChain chain = event.getMessage();
            String message = chain.contentToString();
            ChatBuilder chatBuilder = qaMap.get(sender.getId());
            if (chatBuilder != null) {
                messageService.multiQuestionByIam(event, sender, chatBuilder, message, qaMap);
            } else if (message.startsWith(BotBaseConstants.BotConstants.NAME)) {
                messageService.question(event, message);
            } else if (message.equals(BotBaseConstants.BotConstants.OPEN_Q_A)) {
                messageService.openQA(event, sender, qaMap);
            } else if (message.startsWith(BotBaseConstants.BotConstants.REPEAT)) {
                messageService.repeat(event, sender, message);
            } else if (message.startsWith(BotBaseConstants.BotConstants.MO_YU)) {
                messageService.moYu(event, group);
            } else if (message.startsWith(BotBaseConstants.BotConstants.DRAW)) {
                messageService.generatePicture(event, group, message);
            } else if (message.startsWith(BotBaseConstants.BotConstants.CHANGE_AI)) {
                messageService.changeAi(event, message);
            } else if (BotOrderConstants.REGISTER_USER.equals(message)) {
                messageService.registerUser(event, sender);
            } else if (message.equals("查看指令")) {
                messageService.showCommand(event);
            } else if (message.equals("关闭每日消息")) {
                int size = dailyMessageScheduler.scheduledTaskMap.size();
                int[] array = new int[size];
                for (int i = 0; i < size; i++) {
                    array[i] = i + 1;
                }
                dailyMessageScheduler.stopTask(array);
                event.getSubject().sendMessage("已关闭");
            }
        });
        eventChannel.subscribeAlways(FriendMessageEvent.class, event -> {
            //获取好友消息
            miraiLogger.info(event.getMessage().contentToString());
        });
    }
}