package com.luckj.entity;

import lombok.Data;

@Data
public class Usage {
    // 提示词的数量
    private int prompt_tokens;
    // 完成词的数量
    private int completion_tokens;
    // 总共的token数量
    private int total_tokens;
}