package com.luckj.mybatis;

import com.luckj.config.BigameConfig;
import com.luckj.entity.User;
import com.luckj.mapper.UserMapper;
import com.mysql.cj.jdbc.MysqlDataSource;
import net.mamoe.mirai.utils.MiraiLogger;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class MyBatisLoader {
    private static SqlSessionFactory sqlSessionFactory;
    private static final MiraiLogger MIRAI_LOGGER = MiraiLogger.Factory.INSTANCE.create(MyBatisLoader.class);

    static {
        try {
            BigameConfig bigameConfig = BigameConfig.getInstance();
            if (bigameConfig.getDbType()!= null&& "mysql".equals(bigameConfig.getDbType())){
                sqlSessionFactory = getSqlSessionFactory(bigameConfig.getDbUrl(), bigameConfig.getDbUserName(), bigameConfig.getDbPassword());
                MIRAI_LOGGER.info("======sqlSessionFactory创建成功=====");
            }
        } catch (Exception e) {
            MIRAI_LOGGER.error("======获取配置文件失败=====", e);
        }
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
   