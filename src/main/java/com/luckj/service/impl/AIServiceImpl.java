package com.luckj.service.impl;

import com.luckj.constants.BaseGameConstants;
import com.luckj.service.AIService;
import com.luckj.utils.AliAiUtil;

public class AIServiceImpl implements AIService {
    @Override
    public String question(String s) {
        String replace = s.replace(BaseGameConstants.BotConstants.NAME, "");
        return AliAiUtil.aiQuestion(replace);
    }
}
