package com.luckj.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.core.builder.ChatBuilder;
import com.baidubce.qianfan.model.chat.ChatResponse;
import com.luckj.config.BigameConfig;
import com.luckj.constants.BotBaseConstants;
import com.luckj.entity.ChatSession;
import com.luckj.entity.Messages;
import io.reactivex.annotations.NonNull;
import net.mamoe.mirai.utils.MiraiLogger;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class WenXinAiUtil {
    private static final MiraiLogger MIRAI_LOGGER = MiraiLogger.Factory.INSTANCE.create(WenXinAiUtil.class);
    public static BigameConfig config = BigameConfig.getInstance();
    private static String accessToken = null;
    private static int retryNum = 0;
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient()
            .newBuilder().connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, java.util.concurrent.TimeUnit.MINUTES)
            .writeTimeout(3, java.util.concurrent.TimeUnit.MINUTES).build();

    public static String question(String question) {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            ChatSession chatSession = new ChatSession();
            List<Messages> messageList = new ArrayList<>();
            Messages message = new Messages();
            message.setContent(question);
            message.setKey("1");
            messageList.add(message);
            chatSession.setMessages(messageList);
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(chatSession));
            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/ernie-speed-128k?access_token=" + getAccessToken())
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = HTTP_CLIENT.newCall(request).execute();
            JSONObject jsonObject = JSON.parseObject(response.body().string());
            return jsonObject.getString("result");
        } catch (Exception e) {
            MIRAI_LOGGER.error(e);
            if (retryNum < 3) {
                accessToken = null;
                retryNum++;
                question(question);
            }
            return "我今天有点 mood，请稍后再试";
        }
    }

    public static String questionByIam(String question) {
        Qianfan qianfan = new Qianfan(config.getWen_xin_iam_ak(), config.getWen_xin_iam_sk());
        ChatResponse resp = qianfan.chatCompletion()
                .model("ERNIE-Speed-128K")
                .system(BotBaseConstants.BotConstants.ROLE_SETTING)
                .addMessage("user", question)
                .execute();
        return resp.getResult();
    }

    public static ChatBuilder getChatBuilder() {
        return new Qianfan(config.getWen_xin_iam_ak(), config.getWen_xin_iam_sk()).chatCompletion()
                .model("ERNIE-Speed-128K")
                .system(BotBaseConstants.BotConstants.ROLE_SETTING);
    }

    @NonNull
    public static String multiQuestionByIam(ChatBuilder chatBuilder, String question) {
        chatBuilder.addMessage("user", question);
        ChatResponse resp = chatBuilder.execute();
        String result = resp.getResult();
        chatBuilder.addMessage("assistant", result);
        return result;
    }

    public static String translators(String question) {
        Qianfan qianfan = new Qianfan(config.getWen_xin_iam_ak(), config.getWen_xin_iam_sk());
        ChatResponse resp = qianfan.chatCompletion()
                .model("ERNIE-Speed-128K")
                .system(BotBaseConstants.BotConstants.ROLE_SETTING)
                .addMessage("user", "翻译为英文,只回答翻译后的文字,不要有任何多余的回答:" + question)
                .execute();
        return resp.getResult();
    }

    private static String getAccessToken() throws IOException {
        if (Objects.nonNull(accessToken)) {
            return accessToken;
        }
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + config.getWen_xin_ak()
                + "&client_secret=" + config.getWen_xin_sk());
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        accessToken = JSON.parseObject(response.body().string()).getString("access_token");
        return accessToken;
    }
}
