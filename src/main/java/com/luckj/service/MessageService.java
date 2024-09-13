package com.luckj.service;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.event.events.GroupMessageEvent;

public interface MessageService {

    void registerUser(Member sender, GroupMessageEvent event);

    void question(String message, GroupMessageEvent event);

    void moYu(Group group,GroupMessageEvent event);

    void generatePicture(GroupMessageEvent event, Group group, String message);

    void repeat(String message, GroupMessageEvent event, Member sender);
}
