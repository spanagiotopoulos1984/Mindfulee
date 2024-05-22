package com.stormlord.mindfulee.entity;

import com.stormlord.mindfulee.enums.ActivityCategory;
import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "activity_type")
@Table(name = "activity_type")
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_type_id_gen")
    @SequenceGenerator(name = "activity_type_id_gen", sequenceName = "activity_type_seq", allocationSize = 1)
    private Integer id;

    @Version
    private int version;

    @Column(name = "activity_type_name", nullable = false)
    private String activityTypeName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_category", nullable = false)
    private ActivityCategory activityCategory;

    public ActivityType() {
    }

    public ActivityType(String activityTypeName, ActivityCategory activityCategory) {
        this.activityTypeName = activityTypeName;
        this.activityCategory = activityCategory;
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
        ActivityType that = (ActivityType) o;
        return version == that.version && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
