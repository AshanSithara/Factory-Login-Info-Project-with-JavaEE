package com.proje.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u LEFT JOIN Roles r ON u.userId = r.roleId"),
        @NamedQuery(name = "User.findUsername" , query = "SELECT u FROM User u WHERE u.username = :username")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private int userId;

    @Column(name="USER_NAME",nullable = false)
    private String name;

    @Column(name="USER_USERNAME",nullable = false,unique = true)
    private String username;

    @Column(name = "USER_PASSWORD",nullable = false)
    private String password;

    @Column(name = "USER_OK",nullable = false)
    private int ok;

    @OneToOne
    @JoinColumn(name = "USER_ROLES")
    private Roles roles;

    @ManyToMany(mappedBy = "users",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<LoginPermission> loginPermissions = new ArrayList<LoginPermission>();

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

    public void setLoginPermissions(List<LoginPermission> loginPermissions) {
        this.loginPermissions = loginPermissions;
    }

}
