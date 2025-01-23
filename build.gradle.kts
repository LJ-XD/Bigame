val miraiVersion = "2.16.0"

val kotlinVersion = "1.8.10"

group = "com.luckj"

version = "0.1.0"

plugins {
    kotlin("jvm") version "1.8.10"

    id("net.mamoe.mirai-console") version "2.16.0"
}
repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenCentral()
}
dependencies {
    // 添加 MySQL JDBC 驱动
    implementation("com.mysql:mysql-connector-j:8.3.0") {
        exclude(group = "com.google.protobuf", module = "protobuf-java")
    }

    implementation("org.xerial:sqlite-jdbc:3.47.1.0")

    //Mybatis
    implementation("org.mybatis:mybatis:3.5.13")

    //工具依赖
    implementation("net.mamoe:mirai-core-api:2.16.0")
    implementation("net.mamoe:mirai-console-compiler-common:2.16.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.yaml:snakeyaml:2.0")
    implementation("cn.hutool:hutool-all:5.8.25")
    implementation("com.alibaba:fastjson:2.0.52")
    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("com.baidubce:qianfan:0.1.1"){
        exclude(group = "org.slf4j", module = "slf4j-api")
        exclude(group = "org.slf4j", module = "slf4j-simple")
    }
    implementation("com.alibaba", "dashscope-sdk-java", "2.16.2")
    testImplementation("junit:junit:4.13.1")
}
