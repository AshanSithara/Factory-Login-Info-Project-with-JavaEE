package com.proje.model;

import javax.persistence.*;

@Entity
@Table(name ="roles")
@NamedQueries({
        @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Roles r"),
})
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private int roleId;

    @Column(name = "ROLE_NAME",nullable = false,unique = true)
    private String roleName;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
