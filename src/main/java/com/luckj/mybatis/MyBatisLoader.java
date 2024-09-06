package com.luckj.mybatis;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.luckj.constants.BotConfigConstants;
import com.luckj.entity.BigameConfig;
import com.luckj.entity.User;
import com.luckj.mapper.UserMapper;
import com.luckj.utils.YmlUtils;
import com.mysql.cj.jdbc.MysqlDataSource;
import net.mamoe.mirai.utils.MiraiLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;

/**
 * Batis Util
 *
 * @author lj
 * @date 2024/08/05
 */
public class MyBatisLoader {
    private static SqlSessionFactory sqlSessionFactory;
    private static final MiraiLogger MIRAI_LOGGER = MiraiLogger.Factory.INSTANCE.create(MyBatisLoader.class);
    public static BigameConfig bigameConfig = new BigameConfig();
    static {
        try {
            String configUrl = "config/com.luckj.bigame/bigame.yml";
            try {
                checkConfigFile(configUrl);
                JSONObject jsonObject = YmlUtils.loadYamlAsJsonObject(configUrl);
                bigameConfig = JSON.toJavaObject(jsonObject, BigameConfig.class);
                MIRAI_LOGGER.info("======获取配置文件成功=====");
            } catch (Exception e) {
                MIRAI_LOGGER.error("======配置文件加载失败,开始加载默认配置=====");
            }
            sqlSessionFactory = getSqlSessionFactory(bigameConfig.getDbUrl(), bigameConfig.getDbUserName(), bigameConfig.getDbPassword());
            MIRAI_LOGGER.info("======sqlSessionFactory创建成功=====");
        } catch (Exception e) {
            MIRAI_LOGGER.error("======获取配置文件失败=====", e);
        }

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

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    public static SqlSessionFactory getSqlSessionFactory(String url, String username, String password) throws IOException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.getTypeAliasRegistry().registerAlias(User.class);
        configuration.addMapper(UserMapper.class);
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private static void loadMappers(Configuration configuration) throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mapper/UserMapper.xml");
        XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, "mapper/UserMapper.xml", configuration.getSqlFragments());
        mapperParser.parse();
    }

    private static void checkMapperLoaded(Configuration configuration) {
        Collection<MappedStatement> mappedStatements = configuration.getMappedStatements();
        boolean userMapperFound = false;
        for (MappedStatement ms : mappedStatements) {
            if (ms.getId().startsWith("com.luckj.mapper.UserMapper")) {
                userMapperFound = true;
                break;
            }
        }

        if (!userMapperFound) {
            throw new RuntimeException("UserMapper.xml was not loaded.");
        }
    }
}
   