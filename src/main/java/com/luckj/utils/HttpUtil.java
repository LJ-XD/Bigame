package com.luckj.utils;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class HttpUtil {

    private static final OkHttpClient client = new OkHttpClient();

    /**
     * 发送GET请求
     *
     * @param url 请求的URL
     * @return 响应体字符串
     */
    public static String sendGetRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        System.out.println("发送请求：" + url);
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                System.out.println("请求失败，状态码：" + response);
                throw new RuntimeException("Failed to get response: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error sending GET request", e);
        }
    }

    /**
     * 发送POST请求
     *
     * @param url 请求的URL
     * @param params 请求参数
     * @return 响应体字符串
     */
    public static String sendPostRequest(String url, Map<String, String> params) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        RequestBody requestBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new RuntimeException("Failed to get response: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error sending POST request", e);
        }
    }

    /**
     * 发送带有JSON体的POST请求
     *
     * @param url 请求的URL
     * @param jsonBody JSON格式的请求体
     * @return 响应体字符串
     */
    public static String sendPostRequestWithJson(String url, String jsonBody) {
        RequestBody requestBody = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new RuntimeException("Failed to get response: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error sending POST request with JSON", e);
        }
    }
}
