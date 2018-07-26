package com.proje.dto;

import com.proje.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginPermissionDto {

    private int permissionId;
    private int permissionOk;
    private Date dateOfEntry;
    private Date dateOfOut;
    private Date loginHours;
    private Date outHours;
    private String companyId;
    private List<User> users = new ArrayList<User>();
    private List<Place> places = new ArrayList<Place>();
    private List<IntroductionReason> introductionReasons = new ArrayList<IntroductionReason>();
    private String personelName;
    private int isJobSecurity;

    public LoginPermissionDto(){
    }

    public LoginPermissionDto(LoginPermission loginPermission){
        this.permissionId = loginPermission.getPermissionId();
        this.permissionOk = loginPermission.getPermissionOk();
        this.dateOfEntry = loginPermission.getDateOfEntry();
        this.dateOfOut = loginPermission.getDateOfOut();
        this.loginHours = loginPermission.getLoginHours();
        this.outHours = loginPermission.getOutHours();
        this.companyId = loginPermission.getCompanyId();
        this.personelName = loginPermission.getPersonelName();
        this.isJobSecurity = loginPermission.getIsJobSecurity();
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public int getPermissionOk() {
        return permissionOk;
    }

    public void setPermissionOk(int permissionOk) {
        this.permissionOk = permissionOk;
    }

    public Date getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Date dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public Date getDateOfOut() {
        return dateOfOut;
    }

    public void setDateOfOut(Date dateOfOut) {
        this.dateOfOut = dateOfOut;
    }

    public Date getLoginHours() {
        return loginHours;
    }

    public void setLoginHours(Date loginHours) {
        this.loginHours = loginHours;
    }

    public Date getOutHours() {
        return outHours;
    }

    public void setOutHours(Date outHours) {
        this.outHours = outHours;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<IntroductionReason> getIntroductionReasons() {
        return introductionReasons;
    }

    public void setIntroductionReasons(List<IntroductionReason> introductionReasons) {
        this.introductionReasons = introductionReasons;
    }

    public String getPersonelName() {
        return personelName;
    }

    public void setPersonelName(String personelName) {
        this.personelName = personelName;
    }

    public int getIsJobSecurity() {
        return isJobSecurity;
    }

    public void setIsJobSecurity(int isJobSecurity) {
        this.isJobSecurity = isJobSecurity;
    }

}
