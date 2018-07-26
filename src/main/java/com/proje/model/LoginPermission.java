package com.proje.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="login_permission")
@NamedQueries({
        @NamedQuery(name = "LoginPermission.findAll", query = "SELECT l FROM LoginPermission l")
})
public class LoginPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERMISSION_ID")
    private int permissionId;

    @Column(name = "PERMISSION_OK",nullable = false)
    private int permissionOk;

    @Column(name = "DATE_OF_ENTRY",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfEntry;

    @Column(name = "DATE_OF_OUT",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfOut;

    @Column(name = "LOGIN_HOURS",nullable = false)
    @Temporal(TemporalType.TIME)
    private Date loginHours;

    @Column(name = "OUT_HOURS",nullable = false)
    @Temporal(TemporalType.TIME)
    private Date outHours;

    @Column(name = "COMPANY_NAME",nullable = false)
    private String companyId;

    @ManyToMany
    @JoinTable(name = "LOGIN_PERMISSION_USER",joinColumns = @JoinColumn(name = "LOGIN_PERMISSION_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private List<User> users = new ArrayList<User>();

    @ManyToMany
    @JoinTable(name = "LOGIN_PERMISSION_PLACE",joinColumns = @JoinColumn(name = "LOGIN_PERMISSION_ID"),
            inverseJoinColumns = @JoinColumn(name = "PLACE_ID"))
    private List<Place> places = new ArrayList<Place>();

    @ManyToMany
    @JoinTable(name = "LOGIN_PERMISSION_REASON",joinColumns = @JoinColumn(name = "LOGIN_PERMISSION_ID"),
            inverseJoinColumns = @JoinColumn(name = "REASON_ID"))
    private List<IntroductionReason> introductionReasons = new ArrayList<IntroductionReason>();

    @Column(name = "PERSONEL_NAME",nullable = false)
    private String personelName;

    @Column(name = "IS_JOB_SECURITY",nullable = false)
    private int isJobSecurity;

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


    @Override
    public String toString() {
        return "LoginPermission{" +
                "permissionId=" + permissionId +
                ", permissionOk=" + permissionOk +
                ", dateOfEntry=" + dateOfEntry +
                ", dateOfOut=" + dateOfOut +
                ", loginHours=" + loginHours +
                ", outHours=" + outHours +
                ", companyId=" + companyId +
                ", users=" + users +
                ", places=" + places +
                ", introductionReasons=" + introductionReasons +
                ", personelName='" + personelName + '\'' +
                ", isJobSecurity=" + isJobSecurity +
                '}';
    }
}
