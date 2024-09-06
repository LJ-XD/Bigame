package com.luckj.constants;

/**
 * 机器人配置常量
 *
 * @author lj
 * @date 2024/08/05
 */
public interface BotConfigConstants {
    String MYBATIS_CONFIG = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
            "<!DOCTYPE configuration\n" +
            "        PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\"\n" +
            "        \"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n" +
            "<configuration>\n" +
            "    <properties url=\"file:config/com.luckj.bigame/mybatis.properties\"/>\n" +
            "    <settings>\n" +
            "        <setting name=\"logImpl\" value=\"STDOUT_LOGGING\"/>\n" +
            "    </settings>\n" +
            "    <environments default=\"development\">\n" +
            "        <environment id=\"development\">\n" +
            "            <transactionManager type=\"JDBC\"/>\n" +
            "            <dataSource type=\"POOLED\">\n" +
            "                <property name=\"driver\" value=\"${driver}\"/>\n" +
            "                <property name=\"url\" value=\"${url}\"/>\n" +
            "                <property name=\"username\" value=\"${username}\"/>\n" +
            "                <property name=\"password\" value=\"${password}\"/>\n" +
            "            </dataSource>\n" +
            "        </environment>\n" +
            "    </environments>\n" +
            "    <mappers>\n" +
            "        <package name=\"com/luckj/mapper\"/>\n" +
            "    </mappers>\n" +
            "</configuration>";
    String MYBATIS_PROPERTIES = "driver=com.mysql.cj.jdbc.Driver\n" +
            "url=jdbc:mysql://localhost:3306/bi_game?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true\n" +
            "username=root\n" +
            "password=123456";
    String BIGAME_YML = "dbUrl: jdbc:mysql://localhost:3306/bi_game?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true\n" +
            "dbUserName: root\n" +
            "dbPassword: 123456\n"+
            "master: ";
}
