package com.stormlord.mindfulee.enums;

public enum IntentType {
    SHOULD(5,"Things society tells you need to do. External Reward, but costs."),
    DUTY(7, "Responsibilities and Obligations. Internal Reward, but costs."),
    DESIRE(1,"Things you want to do. External Reward. Gains."),
    VALUE(3,"Things you care about. External Reward. Gains.");

    private final int value;
    private final String desc;

    IntentType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
