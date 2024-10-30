package com.luckj.service.impl;

import com.baidubce.qianfan.core.builder.ChatBuilder;
import com.luckj.config.BigameConfig;
import com.luckj.constants.BotBaseConstants;
import com.luckj.entity.User;
import com.luckj.enums.AiTypeNum;
import com.luckj.service.AIService;
import com.luckj.service.MessageService;
import com.luckj.service.UserService;
import com.luckj.task.DailyMessageScheduler;
import com.luckj.utils.WenXinAiUtil;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

public class MessageServiceImpl implements MessageService {
    private final UserService userService;
    private final AIService aiService;
    private final DailyMessageScheduler dailyMessageScheduler;

    private static Long drawTime = null;
    private static Integer AI_TYPE = 1;
    private final BigameConfig config = BigameConfig.getInstance();


    public MessageServiceImpl() {
        this.aiService = new AIServiceImpl();
        this.userService = new UserServiceImpl();
        this.dailyMessageScheduler = DailyMessageScheduler.getInstance();
    }


    @Override
    public void registerUser(GroupMessageEvent event, Member sender) {
        User user = new User();
        user.setId(sender.getId());
        user.setName(sender.getNick());
        event.getSubject().sendMessage(userService.registerUser(user));
    }

    @Override
    public void question(GroupMessageEvent event, String message) {
        String question = aiService.question(message.substring(2).trim(), AI_TYPE);
        recover(event, question);
    }

    @Override
    public void moYu(GroupMessageEvent event, Group group) {
        event.getSubject().sendMessage(aiService.moYu(group));
    }

    @Override
    public void generatePicture(GroupMessageEvent event, Group group, String message) {
        if (Objects.isNull(drawTime) || drawTime + 1000 * 60 < System.currentTimeMillis()) {
            drawTime = System.currentTimeMillis();
            recover(event, "作画中,请稍后");
            Image image = aiService.generatePicture(group, message.substring(BotBaseConstants.BotConstants.DRAW.length()));
            if (image == null) {
                recover(event, "生成图片失败");
            } else {
                recover(event, image);
            }
        } else {
            recover(event, "冷却中请勿频繁发送");
        }
    }

    @Override
    public void repeat(GroupMessageEvent event, Member sender, String message) {
        if (String.valueOf(sender.getId()).equals(config.getMaster())) {
            sendMsg(event, message.substring(BotBaseConstants.BotConstants.REPEAT.length()).trim());
        }
    }

    @Override
    public void changeAi(GroupMessageEvent event, String message) {
        String aiType = message.substring(BotBaseConstants.BotConstants.CHANGE_AI.length()).trim();
        if (StringUtils.isEmpty(aiType)) {
            sendMsg(event, "请输入AI类型");
        }
        int parsedAiType = Integer.parseInt(aiType);
        AiTypeNum aiTypeNum = null;
        for (AiTypeNum type : AiTypeNum.values()) {
            if (type.getCode() == parsedAiType) {
                aiTypeNum = type;
                AI_TYPE = parsedAiType;
                break;
            }
        }
        if (Objects.nonNull(aiTypeNum)) {
            sendMsg(event, "已切换为" + aiTypeNum.getName());
        } else {
            sendMsg(event, "暂不支持该AI类型");
        }
    }

    @Override
    public void openQA(GroupMessageEvent event, Member sender, Map<Long, ChatBuilder> qaMap) {
        qaMap.put(sender.getId(), WenXinAiUtil.getChatBuilder());
        sendMsg(event, "对话已开启,并将会在10分钟后自动关闭");
        dailyMessageScheduler.close(event, sender.getId(), qaMap);
    }

    @Override
    public void closeQA(Member sender, Map<Long, ChatBuilder> qaMap) {
        qaMap.remove(sender.getId());
    }

    @Override
    public void multiQuestionByIam(GroupMessageEvent event, Member sender, ChatBuilder chatBuilder, String message, Map<Long, ChatBuilder> qaMap) {
        if (message.equals(BotBaseConstants.BotConstants.CLOSE_Q_A)) {
            closeQA(sender, qaMap);
            sendMsg(event, "对话已关闭");
        } else if (message.contains("关闭")) {
            sendMsg(event, "如果想关闭对话请输入：" + BotBaseConstants.BotConstants.CLOSE_Q_A);
        } else {
            recover(event, WenXinAiUtil.multiQuestionByIam(chatBuilder, message));
        }
    }

    @Override
    public void showCommand(GroupMessageEvent event) {
        String commands = "现在支持的指令有:\n" +
                BotBaseConstants.BotConstants.NAME + "(和米若聊天)" + "\n" +
                BotBaseConstants.BotConstants.DRAW + "(让米若画画)" + "\n" +
                BotBaseConstants.BotConstants.MO_YU + "(让米若发一个摸鱼图)" + "\n" +
                BotBaseConstants.BotConstants.OPEN_Q_A + "(和米若开启多轮聊天,米若会记住你之前的问题 ps:单轮聊天不会记住之前的问题)" + "\n" +
                BotBaseConstants.BotConstants.CLOSE_Q_A + "(顾名思义)";
        sendMsg(event, commands);
    }

    private static void sendMsg(GroupMessageEvent event, String message) {
        event.getSubject().sendMessage(message);
    }

    private static void recover(GroupMessageEvent event, String message) {
        event.getSubject().sendMessage(new MessageChainBuilder()
                .append(new QuoteReply(event.getMessage()))
                .append(message)
                .build());
    }

    private static void recover(GroupMessageEvent event, Image image) {
        event.getSubject().sendMessage(new MessageChainBuilder()
                .append(new QuoteReply(event.getMessage()))
                .append(image)
                .build());
    }
}
