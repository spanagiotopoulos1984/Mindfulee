package com.stormlord.mindfulee.model;

import com.stormlord.mindfulee.converter.ActivityTypeConverter;
import com.stormlord.mindfulee.entity.Activity;
import com.stormlord.mindfulee.enums.ActivityStatus;
import com.stormlord.mindfulee.enums.IntentType;

import java.time.LocalDateTime;
import java.util.Objects;

public class ActivityDto {

    private Integer id;
    private int version;
    private String activityName;
    private LocalDateTime dateAdded;
    private LocalDateTime dateStarted;
    private LocalDateTime dateEnded;
    private LocalDateTime dateUpdated;
    private ActivityStatus activityStatus;
    private IntentType intentType;
    private ActivityTypeDto activityType;
    private AppUserDto appUserDto;

    public ActivityDto() {
    }

    public ActivityDto(String activityName, LocalDateTime dateAdded, LocalDateTime dateUpdated, LocalDateTime dateStarted, LocalDateTime dateEnded,
                       ActivityStatus activityStatus, IntentType intentType, ActivityTypeDto activityType, AppUserDto appUserDto) {
        this.activityName = activityName;
        this.dateAdded = dateAdded;
        this.dateUpdated = dateUpdated;
        this.activityStatus = activityStatus;
        this.intentType = intentType;
        this.activityType = activityType;
        this.dateStarted = dateStarted;
        this.dateEnded = dateEnded;
        this.appUserDto = appUserDto;
    }

    public ActivityDto(Activity activity) {
        this.activityName = activity.getActivityName();
        this.dateAdded = activity.getDateAdded();
        this.dateUpdated = activity.getDateUpdated();
        this.activityStatus = activity.getActivityStatus();
        this.intentType = activity.getIntentType();
        this.id = activity.getId();
        this.version = activity.getVersion();
        this.activityType = ActivityTypeConverter.activityTypeDtoHashMap.get(ActivityTypeConverter.activityIdToName.get(activity.getActivityType()));
        this.dateEnded = activity.getDateEnded();
        this.dateStarted = activity.getDateStarted();
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

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public ActivityTypeDto getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityTypeDto activityType) {
        this.activityType = activityType;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public IntentType getIntentType() {
        return intentType;
    }

    public void setIntentType(IntentType intentType) {
        this.intentType = intentType;
    }

    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(ActivityStatus activityStatus) {
        this.activityStatus = activityStatus;
    }

    public LocalDateTime getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(LocalDateTime dateStarted) {
        this.dateStarted = dateStarted;
    }

    public LocalDateTime getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(LocalDateTime dateEnded) {
        this.dateEnded = dateEnded;
    }

    public AppUserDto getAppUserDto() {
        return appUserDto;
    }

    public void setAppUserDto(AppUserDto appUserDto) {
        this.appUserDto = appUserDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityDto activity = (ActivityDto) o;
        return version == activity.version && Objects.equals(id, activity.id) && Objects.equals(activityName, activity.activityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, activityName);
    }

    @Override
    public String toString() {
        return "ActivityDto{" +
                "id=" + id +
                ", version=" + version +
                ", activityName='" + activityName + '\'' +
                ", dateAdded=" + dateAdded +
                ", dateStarted=" + dateStarted +
                ", dateEnded=" + dateEnded +
                ", dateUpdated=" + dateUpdated +
                ", activityStatus=" + activityStatus +
                ", intentType=" + intentType +
                ", activityType=" + activityType +
                ", appUserDto=" + appUserDto +
                '}';
    }
}
