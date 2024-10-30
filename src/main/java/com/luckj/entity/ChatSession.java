package com.luckj.entity;

import com.luckj.constants.BotBaseConstants;
import lombok.Data;

import java.util.List;

@Data
public class ChatSession {

    private List<Messages> messages;
    private double temperature = 0.95;
    private double top_p = 0.7;
    private int penalty_score = 1;
    private boolean collapsed = true;
    private String system= BotBaseConstants.BotConstants.ROLE_SETTING;
}