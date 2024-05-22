package com.stormlord.mindfulee.enums;

public enum ActivityStatus {
    ADDED("Added"),
    DOING("Doing"),
    COMPLETED("Completed"),
    POSTPONED("Postponed"),
    CANCELED("Canceled");

    private final String desc;

    ActivityStatus(String desc) {
        this.desc = desc;
    }

    public static ActivityStatus findByValue(Integer key) {
        for (ActivityStatus status : ActivityStatus.values()) {
            if (status.ordinal() == key) {
                return status;
            }
        }
        return ActivityStatus.ADDED;
    }

    public String getDesc() {
        return desc;
    }
}
