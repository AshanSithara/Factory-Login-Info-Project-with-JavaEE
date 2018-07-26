package com.proje.dto;

import com.proje.model.LoginPermission;
import com.proje.model.Roles;
import com.proje.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private int userId;
    private String name;
    private String username;
    private String password;
    private int ok;
    private Roles roles;
    private List<LoginPermission> loginPermissions = new ArrayList<LoginPermission>();

    public UserDto() {

    }

    public UserDto(User user) {
        this.name = user.getName();
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.ok = user.isOk();
        this.roles = user.getRoles();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public int getOk() {
        return ok;
    }

    public List<LoginPermission> getLoginPermissions() {
        return loginPermissions;
    }

    public void setLoginPermissions(List<LoginPermission> loginPermissions) {
        this.loginPermissions = loginPermissions;
    }
}
