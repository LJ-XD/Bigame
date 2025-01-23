package com.luckj.constants;

/**
 * 机器人配置常量
 *
 * @author lj
 * @date 2024/08/05
 */
public interface BotConfigConstants {
    String BIGAME_YML = "dbUrl: jdbc:mysql://localhost:3306/bi_game?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true\n" +
            "dbUserName: root\n" +
            "dbPassword: 123456\n"+
            "master: \n"+
            "aiApiKey: \n"+
            "botQQ: \n"+
            "wen_xin_ak: \n"+
            "wen_xin_sk: \n"+
            "wen_xin_iam_ak: \n"+
            "wen_xin_iam_sk: \n"+
            "dbType: \n"
            ;
    String DB_URL = "jdbc:sqlite:data/com.luckj/bigame.db";
}
