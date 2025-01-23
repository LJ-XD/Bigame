package com.luckj.utils;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesis;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisParam;
import com.alibaba.dashscope.aigc.imagesynthesis.ImageSynthesisResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.luckj.config.BigameConfig;
import com.luckj.constants.BotBaseConstants;
import net.mamoe.mirai.utils.MiraiLogger;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;


public class AliAiUtil {
    private final static MiraiLogger miraiLogger = MiraiLogger.Factory.INSTANCE.create(AliAiUtil.class);
    private static final String AI_API_KEY = BigameConfig.getInstance().getAiApiKey();
    private static final OkHttpClient CLIENT = new OkHttpClient();

    public static GenerationResult callWithMessage(String question) throws ApiException, NoApiKeyException, InputRequiredException {
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
                .apiKey(AI_API_KEY)
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

    public static String generatePicture(String prompt) {
        String fileName = null;
        try {
            ImageSynthesis is = new ImageSynthesis();
            ImageSynthesisParam param =
                    ImageSynthesisParam.builder()
                            .model("flux-merged")
                            .n(1)
                            .apiKey(AI_API_KEY)
                            .size("1024*1024")
                            .prompt(prompt)
                            .negativePrompt("garfield")
                            .build();
            ImageSynthesisResult result = is.call(param);
            System.out.println(result);
            // save image to local files.
            for (Map<String, String> item : result.getOutput().getResults()) {
                String paths = new URL(item.get("url")).getPath();
                String[] parts = paths.split("/");
                fileName = parts[parts.length - 1];
                Request request = new Request.Builder()
                        .url(item.get("url"))
                        .build();
                try (Response response = CLIENT.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }
                    YmlUtils.checkAndCreateDirectory(BotBaseConstants.BotConfigConstants.SAVE_DIRECTORY);
                    Path file = Paths.get(BotBaseConstants.BotConfigConstants.SAVE_DIRECTORY + "/" + fileName);
                    Files.write(file, response.body().bytes());
                }
            }
        } catch (Exception e) {
            miraiLogger.error("ai作画失败", e);
        }
        return fileName;
    }

}
