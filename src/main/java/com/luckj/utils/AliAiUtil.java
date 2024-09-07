package com.luckj.utils;

// Refer to the document for workspace information: https://help.aliyun.com/document_detail/2746874.html

// Copyright (c) Alibaba, Inc. and its affiliates.

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.luckj.Bigame;
import com.luckj.config.BigameConfig;
import net.mamoe.mirai.utils.MiraiLogger;

import java.util.Arrays;

public class AliAiUtil {
    private final static MiraiLogger miraiLogger = MiraiLogger.Factory.INSTANCE.create(Bigame.class);

    public static GenerationResult callWithMessage(String question) throws ApiException, NoApiKeyException, InputRequiredException {
        String aiApiKey = BigameConfig.getInstance().getAiApiKey();
        Generation gen = new Generation();

        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("You are a helpful assistant.")
                .build();

        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(question)
                .build();

        GenerationParam param = GenerationParam.builder()
                .model("qwen-max")
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .topK(50)
                .temperature(0.8f)
                .topP(0.8)
                .seed(1234)
                .apiKey(aiApiKey)
                .build();

        return gen.call(param);
    }

    public static String aiQuestion(String question) {
        try {
            GenerationResult result = callWithMessage(question);
            Message message = result.getOutput().getChoices().get(0).getMessage();
            return message.getContent();
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            miraiLogger.error("ai回答失败", e);
            return "网络有点波动,请用力拍一拍你的手机,可能会好一点(";
        }
    }
}
