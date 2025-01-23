package com.luckj.service.impl;

import cn.hutool.core.util.StrUtil;
import com.luckj.constants.BotBaseConstants;
import com.luckj.enums.AiTypeNum;
import com.luckj.service.AIService;
import com.luckj.utils.AliAiUtil;
import com.luckj.utils.WenXinAiUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;


public class AIServiceImpl implements AIService {

    public static final AIServiceImpl INSTANCE = new AIServiceImpl();

    private AIServiceImpl() {
    }

    @Override
    public String question(String s, int type) {
        if (type == AiTypeNum.WEN_XIN_AI.getCode()) {
            return WenXinAiUtil.questionByIam(s);
        } else if (type == AiTypeNum.ALI_AI.getCode()) {
            return AliAiUtil.aiQuestion(s);
        } else {
            return "模型出问题了快找人来看看吧\uD83D\uDE35";
        }
    }

    @Override
    public Image generatePicture(Contact contact, String prompt) {
        boolean containsChinese = StrUtil.containsAny(prompt, "一", "龥");
        if (containsChinese) {
            prompt = WenXinAiUtil.translators(prompt);
        }
        String imageUrl = AliAiUtil.generatePicture(prompt);
        File file = new File(BotBaseConstants.BotConfigConstants.SAVE_DIRECTORY + "/" + imageUrl);
        return ExternalResource.uploadAsImage(file, contact);
    }
}

