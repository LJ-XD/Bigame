package com.luckj.games.number.bo;

import com.luckj.constants.BotBaseConstants;
import lombok.Data;

@Data
public class NumGameBo {
    /**
     * 目标数字
     */
    private int goalNum;
    /**
     * 猜测数字
     */
    private int guessNum;
    /**
     * 玩家名
     */
    private String userName;
    /**
     * 另一个玩家名称/机器人名称
     */
    private String botName= BotBaseConstants.BotConstants.NAME;

    public int getGoalNum() {
        return goalNum;
    }

    public void setGoalNum(int goalNum) {
        this.goalNum = goalNum;
    }

    public int getGuessNum() {
        return guessNum;
    }

    public void setGuessNum(int guessNum) {
        this.guessNum = guessNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }
}
