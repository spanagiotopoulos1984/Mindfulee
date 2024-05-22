package com.stormlord.mindfulee.model;

import com.stormlord.mindfulee.entity.AppUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AppUserDto {

    private Integer id;
    private int version;
    private String username;
    private String password;
    private List<ActivityDto> userActivities = new ArrayList<>();

    public AppUserDto() {
    }

    public AppUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AppUserDto(AppUser user) {
        this.id = user.getId();
        this.version = user.getVersion();
        this.username = user.getUsername();
        this.password = null;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ActivityDto> getUserActivities() {
        return userActivities;
    }

    public void setUserActivities(List<ActivityDto> userActivities) {
        this.userActivities = userActivities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUserDto that = (AppUserDto) o;
        return version == that.version && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
