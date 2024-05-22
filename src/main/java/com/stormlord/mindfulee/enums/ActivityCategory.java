package com.stormlord.mindfulee.enums;


public enum ActivityCategory {
    REST("Rest"),
    INTROSPECTION("Introspection"),
    PERSONAL_DEVELOPMENT("Personal Development"),
    WORK("Work"),
    CHORES("Chores"),
    HOBBY("Hobby"),
    SOCIAL("Social"),
    FAMILY("Family"),
    FRIENDS("Friends"),
    GAMING("Gaming"),
    OTHER("Other");

    private final String name;


    public static ActivityCategory findByValue(int value) {
        for (ActivityCategory category : ActivityCategory.values()) {
            if(category.ordinal()==value){
                return category;
            }
        }
        return ActivityCategory.OTHER;
    }

    ActivityCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
