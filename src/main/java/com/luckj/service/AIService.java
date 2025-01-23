package com.luckj.service;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;

public interface AIService {
    String question(String s, int type);

    Image generatePicture(Contact contact, String prompt);
}
