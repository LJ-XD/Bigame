package com.luckj.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * yml 实用程序
 *
 * @author lj
 * @date 2024/08/06
 */
public class YmlUtils {
    public static Map<String, Object> loadYamlAsJsonObject(String filePath) throws IOException {
        return new Yaml().load(Files.newInputStream(Paths.get(filePath)));
    }

    /**
     * 检查目录是否存在，如果不存在则创建。
     *
     * @param directoryPath 目录路径
     */
    public static void checkAndCreateDirectory(String directoryPath) {
        Path path = Paths.get(directoryPath);
        try {
            // 检查路径是否存在
            if (!Files.exists(path)) {
                // 创建目录
                Files.createDirectories(path);
                System.out.println("目录已创建: " + directoryPath);
            }
        } catch (IOException e) {
            System.err.println("无法创建目录: " + directoryPath);
            e.printStackTrace();
        }
    }
}
