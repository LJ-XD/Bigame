package com.luckj.entity;

import lombok.Data;
import com.luckj.entity.Usage;

@Data
public class Info {
    // 使用情况统计
    private Usage usage;
    // 完成请求的ID
    private String id;
    // 对象类型（如chat.completion）
    private String object;
    // 创建完成的时间戳
    private long created;
    // 句子ID
    private int sentence_id;
    // 是否为最终结果
    private boolean is_end;
    // 是否被截断
    private boolean is_truncated;
    // 结果字段（可能是空字符串）
    private String result;
    // 是否需要清除历史记录
    private boolean need_clear_history;
    // 是否有错误
    private boolean error;
}