package com.stormlord.mindfulee.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "app_user")
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_id_gen")
    @SequenceGenerator(name = "app_user_id_gen", sequenceName = "app_user_seq", allocationSize = 1)
    private Integer id;

    @Version
    private int version;

    @Column
    private String username;

    @Column
    private String password;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="appUser")
    private Set<Activity> userActivities = new HashSet<>();


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

    public Set<Activity> getUserActivities() {
        return userActivities;
    }

    public void setUserActivities(Set<Activity> userActivities) {
        this.userActivities = userActivities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return id == appUser.id && version == appUser.version && Objects.equals(username, appUser.username) && Objects.equals(password, appUser.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, username, password);
    }
}
