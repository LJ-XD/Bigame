package com.luckj.service;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;

public interface AIService {
    String question(String s);
    Image generatePicture(Contact contact, String prompt);

    Image moYu(Contact contact);
}
