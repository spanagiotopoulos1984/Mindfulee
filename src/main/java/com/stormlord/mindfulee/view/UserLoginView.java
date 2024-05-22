package com.stormlord.mindfulee.view;

import com.stormlord.mindfulee.bean.UserBn;
import com.stormlord.mindfulee.entity.AppUser;
import com.stormlord.mindfulee.model.AppUserDto;
import com.stormlord.mindfulee.service.UserService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.PrimeFaces;

import java.io.IOException;
import java.io.Serializable;

@Named("userLoginView")
@ViewScoped
public class UserLoginView implements Serializable {

    @Inject
    private UserBn userBn;

    @Inject
    private UserService userService;

    private String username;

    private String password;

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

    public void register() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        AppUser au = new AppUser();
        au.setPassword(password);
        au.setUsername(username);
        try {
            userService.createAppUser(au);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "User Registered", username);
            context.addMessage(null, message);
            context.getExternalContext().getFlash().setKeepMessages(true);

            context.getExternalContext().redirect("./index.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Unable to Register User.", "Unable to Register User. Maybe username exists?"));
    }

    public void login() throws IOException {
        FacesMessage message;
        FacesContext context = FacesContext.getCurrentInstance();

        AppUser u = userService.findByUsername(username);

        if (u != null) {
            if (checkPass(password, u.getPassword())) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
                context.addMessage(null, message);
                PrimeFaces.current().ajax().addCallbackParam("loggedIn", true);
                userBn.setLoggedIn(true);
                userBn.setAppUser(new AppUserDto(u));
                context.getExternalContext().redirect("./app/main.xhtml");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Username or password", username);
                context.addMessage(null, message);
            }
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Non-existent User", username);
            context.addMessage(null, message);
        }
    }

    public UserBn getUserBn() {
        return userBn;
    }

    public void setUserBn(UserBn userBn) {
        this.userBn = userBn;
    }

    private boolean checkPass(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public void registerAction() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./register.xhtml");
    }

    public void returnToLogin() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./index.xhtml");
    }
}
