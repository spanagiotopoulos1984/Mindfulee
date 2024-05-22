package com.stormlord.mindfulee.entity;

import com.stormlord.mindfulee.enums.ActivityStatus;
import com.stormlord.mindfulee.enums.IntentType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "activity")
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_id_gen")
    @SequenceGenerator(name = "activity_id_gen", sequenceName = "activity_seq", allocationSize = 1)
    private Integer id;

    @Version
    private int version;

    @Column(name = "activity_name", nullable = false)
    private String activityName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_status", nullable = false)
    private ActivityStatus activityStatus;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "intent_type", nullable = false)
    private IntentType intentType;

    @Column(name = "activity_type_id", nullable = false)
    private Integer activityType;

    @ManyToOne(cascade=CascadeType.ALL, optional=true, fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private AppUser appUser;

    @Column(name = "date_added", nullable = false)
    private LocalDateTime dateAdded;

    @Column(name = "date_started", nullable = false)
    private LocalDateTime dateStarted;

    @Column(name = "date_ended", nullable = false)
    private LocalDateTime dateEnded;

    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated;

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

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
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

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return version == activity.version && Objects.equals(id, activity.id) && Objects.equals(activityName, activity.activityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, activityName);
    }
}
