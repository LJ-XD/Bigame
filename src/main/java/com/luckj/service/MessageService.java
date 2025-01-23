package com.luckj.service;

import com.baidubce.qianfan.core.builder.ChatBuilder;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.util.Map;

public interface MessageService {

    void registerUser(GroupMessageEvent event, Member sender);

    void question(GroupMessageEvent event, String message);

    void generatePicture(GroupMessageEvent event, Group group, String message);

    void repeat(GroupMessageEvent event, Member sender, String message);

    void changeAi(GroupMessageEvent event, String message);

    void openQA(GroupMessageEvent event,Member sender, Map<Long, ChatBuilder> qaMap);

    void closeQA(Member sender, Map<Long, ChatBuilder> qaMap);

    void multiQuestionByIam(GroupMessageEvent event,Member sender, ChatBuilder chatBuilder, String message, Map<Long, ChatBuilder> qaMap);

    void showCommand(GroupMessageEvent event);

    void customizeAnswers(GroupMessageEvent event, String message);
}
