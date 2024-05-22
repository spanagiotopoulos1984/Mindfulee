package com.stormlord.mindfulee.model;

import com.stormlord.mindfulee.entity.ActivityType;
import com.stormlord.mindfulee.enums.ActivityCategory;

import java.util.Objects;

public class ActivityTypeDto {

    private Integer id;
    private int version;
    private String activityTypeName;
    private ActivityCategory activityCategory;

    public ActivityTypeDto() {
    }

    public ActivityTypeDto(String activityTypeName, ActivityCategory activityCategory) {
        this.activityTypeName = activityTypeName;
        this.activityCategory = activityCategory;
    }

    public ActivityTypeDto(ActivityType activityType) {
        this.id = activityType.getId();
        this.version = activityType.getVersion();
        this.activityTypeName = activityType.getActivityTypeName();
        this.activityCategory = activityType.getActivityCategory();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public ActivityCategory getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(ActivityCategory activityCategory) {
        this.activityCategory = activityCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityTypeDto that = (ActivityTypeDto) o;
        return version == that.version && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    @Override
    public String toString() {
        return activityTypeName;
    }
}
