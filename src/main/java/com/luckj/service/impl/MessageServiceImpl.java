package com.luckj.service.impl;

import com.luckj.config.BigameConfig;
import com.luckj.constants.BaseGameConstants;
import com.luckj.entity.User;
import com.luckj.service.AIService;
import com.luckj.service.MessageService;
import com.luckj.service.UserService;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

import java.util.Objects;

public class MessageServiceImpl implements MessageService {
    private UserService userService;
    private AIService aiService;

    private static Long drawTime = null;
    private BigameConfig config = BigameConfig.getInstance();


    public MessageServiceImpl(UserService userService, AIService aiService) {
        this.aiService = new AIServiceImpl();
        this.userService = new UserServiceImpl();
    }

    public MessageServiceImpl() {
    }

    @Override
    public void registerUser(Member sender, GroupMessageEvent event) {
        User user = new User();
        user.setId(sender.getId());
        user.setName(sender.getNick());
        event.getSubject().sendMessage(userService.registerUser(user)); // 回复消息
    }

    @Override
    public void question(String message, GroupMessageEvent event) {
        String question = aiService.question(message.substring(2));
        MessageChain chainQuote = new MessageChainBuilder()
                .append(new QuoteReply(event.getMessage()))
                .append(question)
                .build();
        event.getSubject().sendMessage(chainQuote);
    }

    @Override
    public void moYu(Group group, GroupMessageEvent event) {
        event.getSubject().sendMessage(aiService.moYu(group));
    }

    @Override
    public void generatePicture(GroupMessageEvent event, Group group, String message) {
        if (Objects.isNull(drawTime) || drawTime + 1000 * 60 < System.currentTimeMillis()) {
            drawTime = System.currentTimeMillis();
            recover(event, "作画中,请稍后");
            Image image = aiService.generatePicture(group, message.substring(BaseGameConstants.BotConstants.DRAW.length()));
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
    public void repeat(String message, GroupMessageEvent event, Member sender) {
        if (String.valueOf(sender.getId()).equals(config.getMaster())) {
            sendMsg(message.substring(BaseGameConstants.BotConstants.REPEAT.length()), event);
        }
    }

    private static void sendMsg(String message, GroupMessageEvent event) {
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
