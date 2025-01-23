package com.luckj.utils;

import com.alibaba.fastjson2.JSON;
import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.core.builder.ChatBuilder;
import com.baidubce.qianfan.model.chat.ChatResponse;
import com.luckj.config.BigameConfig;
import com.luckj.constants.BotBaseConstants;
import io.reactivex.annotations.NonNull;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class WenXinAiUtil {
    public static BigameConfig config = BigameConfig.getInstance();
    private static String accessToken = null;
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient()
            .newBuilder().connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, java.util.concurrent.TimeUnit.MINUTES)
            .writeTimeout(3, java.util.concurrent.TimeUnit.MINUTES).build();
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
                .model("ERNIE-Speed-128K").system("翻译为纯英文,只回答翻译后的英语,不要有任何多余的回答;")
                .addMessage("user", "画"+question)
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
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }else if (Objects.isNull(response.body())) {
            throw new IOException("Unexpected code " + response);
        }
        accessToken = JSON.parseObject(response.body().string()).getString("access_token");
        return accessToken;
    }
}
