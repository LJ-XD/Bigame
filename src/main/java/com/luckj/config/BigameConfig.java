package com.luckj.config;

import com.alibaba.fastjson2.JSON;
import com.luckj.constants.BotConfigConstants;
import com.luckj.utils.YmlUtils;
import lombok.Data;
import net.mamoe.mirai.utils.MiraiLogger;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

/**
 * Bigame配置
 *
 * @author lj
 * @date 2024/08/06
 */
@Data
public class BigameConfig {

    private static final MiraiLogger MIRAI_LOGGER = MiraiLogger.Factory.INSTANCE.create(BigameConfig.class);
    private static BigameConfig bigameConfig;

    private String dbUrl = "jdbc:mysql://localhost:3306/bi_game?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true";
    private String dbUserName = "root";
    private String dbPassword = "123456";
    private String master;
    private String aiApiKey;


    public BigameConfig() {
    }

    public static BigameConfig getInstance() {
        if (Objects.nonNull(bigameConfig)) {
            return bigameConfig;
        }
        try {
            String configUrl = "config/com.luckj.bigame/bigame.yml";
            checkConfigFile(configUrl);
            Map<String, Object> yaml = YmlUtils.loadYamlAsJsonObject(configUrl);
            bigameConfig = JSON.parseObject(JSON.toJSONString(yaml), BigameConfig.class);
        } catch (IOException e) {
            MIRAI_LOGGER.error("BigameConfig读取异常:", e);
        }
        return bigameConfig;
    }

    private static void checkConfigFile(String url) {
        File configFile = new File(url);
        if (!configFile.exists()) {
            MIRAI_LOGGER.error("======找不到配置文件,开始创建=====");
            try {
                boolean directoryCreated = configFile.getParentFile().mkdirs();
                boolean fileCreated = configFile.createNewFile();
                if (directoryCreated || fileCreated) {
                    MIRAI_LOGGER.info("======配置文件创建成功=====");
                    if (StringUtils.isNotBlank(BotConfigConstants.BIGAME_YML)) {
                        writeContentToFile(configFile, BotConfigConstants.BIGAME_YML);
                    }
                } else {
                    MIRAI_LOGGER.error("======配置文件创建失败=====");
                }
            } catch (IOException e) {
                MIRAI_LOGGER.error("======配置文件创建异常=====", e);
            }
        }
    }

    private static void writeContentToFile(File file, String content) throws IOException {
        Files.write(Paths.get(file.getAbsolutePath()), content.getBytes());
    }
}
