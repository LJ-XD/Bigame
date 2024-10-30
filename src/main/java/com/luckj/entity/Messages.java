package com.luckj.entity;

import lombok.Data;

@Data
public class Messages {
    private String role = "user";
    // 消息的内容
    private String content;
    // 消息的唯一标识符
    private String key;
    // 附加的消息信息
    private Info info;
    // 关联的响应ID
    private String as_id;
}
