package com.luckj.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * yml 实用程序
 *
 * @author lj
 * @date 2024/08/06
 */
public class YmlUtils {
    public static JSONObject loadYamlAsJsonObject(String filePath) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Object> map = yaml.load(Files.newInputStream(Paths.get(filePath)));
        String jsonString = JSON.toJSONString(map);
        return JSON.parseObject(jsonString);
    }
}
