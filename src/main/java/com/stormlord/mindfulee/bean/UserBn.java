package com.stormlord.mindfulee.bean;

import com.stormlord.mindfulee.model.AppUserDto;
import jakarta.el.MethodExpression;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

@Named("userBn")
@SessionScoped
public class UserBn implements Serializable {

    private AppUserDto appUser;
    private boolean loggedIn = false;


    private LocalDateTime selectedDate = LocalDateTime.now();

    public AppUserDto getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDto appUser) {
        this.appUser = appUser;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public LocalDateTime getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDateTime selectedDate) {
        this.selectedDate = selectedDate;
    }

    public void logout() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
    }
}
