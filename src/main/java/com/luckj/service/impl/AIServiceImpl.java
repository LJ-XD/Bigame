package com.luckj.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.luckj.config.BigameConfig;
import com.luckj.enums.AiTypeNum;
import com.luckj.service.AIService;
import com.luckj.utils.AliAiUtil;
import com.luckj.utils.WenXinAiUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import net.mamoe.mirai.utils.MiraiLogger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.luckj.utils.AliAiUtil.SAVE_DIRECTORY;

public class AIServiceImpl implements AIService {
    private static String moYuUrl = "https://j4u.ink/moyuya";
    private static OkHttpClient client = new OkHttpClient();
    private static final MiraiLogger MIRAI_LOGGER = MiraiLogger.Factory.INSTANCE.create(AIServiceImpl.class);


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
        File file = new File(SAVE_DIRECTORY + "/" + imageUrl);
        return ExternalResource.uploadAsImage(file, contact);

    }

    @Override
    public Image moYu(Contact contact) {
        try {
            Request request = new Request.Builder()
                    .url(moYuUrl)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("Request failed with code " + response.code());
                }
                assert response.body() != null;
                JSONObject jsonObject = JSON.parseObject(response.body().string());
                String imgUrl = jsonObject.getJSONObject("data").getString("img_url");

                if (StringUtils.isBlank(imgUrl)) {
                    return null;
                }
                URL imgURL = new URL(imgUrl);
                try (InputStream in = imgURL.openStream()) {
                    return ExternalResource.uploadAsImage(in, contact);
                }
            }
        } catch (IOException e) {
            MIRAI_LOGGER.error("Error occurred while fetching image from URL", e);
        }
        return null;
    }
}

