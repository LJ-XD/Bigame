package com.luckj.enums;

public enum AiTypeNum {
    WEN_XIN_AI(1, "文心一言", "文心一言"),
    ALI_AI(2, "通义千问", "阿里云AI"),
    ;

    AiTypeNum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    private int code;
    private String name;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
