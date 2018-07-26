package com.proje.dto;

import com.proje.model.Roles;
import com.proje.model.User;

public class RoleDto {

    private int roleId;
    private String roleName;

    public RoleDto(Roles role){
        this.roleId = role.getRoleId();
        this.roleName = role.getRoleName();
    }

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
