package com.luckj.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luckj.constants.BotBaseConstants;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JsonUtils {

    private static final Gson GSON = new Gson();

    /**
     * 将Map对象转换为JSON字符串并保存到文件。
     *
     * @param filePath 文件路径
     * @param map      要保存的Map对象
     */
    public static void saveMapToJsonFile(String filePath, Map<String, Object> map) {
        YmlUtils.checkAndCreateDirectory(filePath+BotBaseConstants.BotConfigConstants.BIGAME_JSON);
        String jsonString = GSON.toJson(map);
        try (FileWriter writer = new FileWriter(filePath+BotBaseConstants.BotConfigConstants.BIGAME_JSON)) {
            writer.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException("无法将数据保存到文件: " + filePath+BotBaseConstants.BotConfigConstants.BIGAME_JSON, e);
        }
    }

    /**
     * 从文件中读取JSON字符串并转换为Map对象。
     *
     * @param filePath 文件路径
     * @return 解析后的Map对象
     */
    public static Map<String, Object> loadMapFromJsonFile(String filePath) {
        YmlUtils.checkAndCreateDirectory(filePath);
        Path path = Paths.get(filePath+BotBaseConstants.BotConfigConstants.BIGAME_JSON);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                System.out.println("文件已创建: " + filePath+BotBaseConstants.BotConfigConstants.BIGAME_JSON);
            } catch (IOException e) {
                throw new RuntimeException("无法创建文件: " + filePath+BotBaseConstants.BotConfigConstants.BIGAME_JSON, e);
            }
        }
        try (FileReader reader = new FileReader(filePath+BotBaseConstants.BotConfigConstants.BIGAME_JSON)) {
            return GSON.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
        } catch (IOException e) {
            throw new RuntimeException("无法从文件读取数据: " + filePath+BotBaseConstants.BotConfigConstants.BIGAME_JSON, e);
        }
    }

    // 测试方法
    public static void main(String[] args) {
        String filePath = "complexData.json";

        // 初始数据
        Map<String, Object> initialMap = new LinkedHashMap<>();
        initialMap.put("name", "张三");
        initialMap.put("age", 30);

        List<String> hobbies = new LinkedList<>();
        hobbies.add("读书");
        hobbies.add("旅行");
        initialMap.put("hobbies", hobbies);

        Map<String, Integer> scores = new HashMap<>();
        scores.put("数学", 90);
        scores.put("英语", 85);
        initialMap.put("scores", scores);

        // 创建一个自定义对象
        ExampleData initialData = new ExampleData();
        initialData.setName("李四");
        initialData.setHobbies(List.of("编程", "音乐"));
        initialData.setScores(Map.of("物理", 95, "化学", 92));
        initialMap.put("exampleData", initialData);

        // 保存初始数据到文件
        JsonUtils.saveMapToJsonFile(filePath, initialMap);
        System.out.println("初始数据已成功保存到文件: " + filePath);

        // 修改数据
        Map<String, Object> updatedMap = JsonUtils.loadMapFromJsonFile(filePath);
        updatedMap.put("age", 31); // 修改年龄
        updatedMap.put("newKey", "newValue"); // 添加新键值对

        // 更新文件内容
        JsonUtils.saveMapToJsonFile(filePath, updatedMap);
        System.out.println("数据已更新并保存到文件: " + filePath);

        // 再次读取并打印数据
        Map<String, Object> finalMap = JsonUtils.loadMapFromJsonFile(filePath);
        System.out.println("最终数据: " + finalMap);
    }
}

// 示例数据类
class ExampleData {
    private String name;
    private List<String> hobbies;
    private Map<String, Integer> scores;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "ExampleData{" +
                "name='" + name + '\'' +
                ", hobbies=" + hobbies +
                ", scores=" + scores +
                '}';
    }

}
