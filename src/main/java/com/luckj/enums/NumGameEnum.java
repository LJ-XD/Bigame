package com.luckj.enums;

public enum NumGameEnum {
    NUM_GAME("num_game", "猜数字游戏"),
    ;

    private String code;
    private String desc;

    NumGameEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
