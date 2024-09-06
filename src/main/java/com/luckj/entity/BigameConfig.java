package com.luckj.entity;

import java.util.Objects;

/**
 * Bigame配置
 *
 * @author lj
 * @date 2024/08/06
 */
public class BigameConfig {
    private String dbUrl = "jdbc:mysql://localhost:3306/bi_game?useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&allowMultiQueries=true&allowPublicKeyRetrieval=true";
    private String dbUserName = "root";
    private String dbPassword = "123456";
    private String master="";

    public BigameConfig(String dbUrl, String dbUserName, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUserName = dbUserName;
        this.dbPassword = dbPassword;
    }

    public BigameConfig() {
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BigameConfig that = (BigameConfig) o;
        return dbUrl.equals(that.dbUrl) && dbUserName.equals(that.dbUserName) && dbPassword.equals(that.dbPassword) && master.equals(that.master);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dbUrl, dbUserName, dbPassword, master);
    }

    @Override
    public String toString() {
        return "BigameConfig{" +
                "dbUrl='" + dbUrl + '\'' +
                ", dbUserName='" + dbUserName + '\'' +
                ", dbPassword='" + dbPassword + '\'' +
                ", master='" + master + '\'' +
                '}';
    }
}
