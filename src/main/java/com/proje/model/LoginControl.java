package com.proje.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "logincontrol")
@NamedQueries({
        @NamedQuery(name = "LoginControl.findAll", query = "SELECT l FROM LoginControl l"),
        @NamedQuery(name = "LoginControl.findAllWithCompanyName", query = "SELECT l FROM LoginControl l WHERE l.companyName =:companyName"),
        @NamedQuery(name = "LoginControl.findAllWithPermissionId",
                query = "SELECT l FROM LoginControl l LEFT JOIN User u ON l.user = u.userId " +
                        "WHERE l.loginPermissionId =:loginPermissionId AND u.userId =:userId")
})
public class LoginControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOGIN_CONTROL_ID")
    private int loginContolId;

    @OneToOne
    @JoinColumn(name = "LOGIN_CONTROL_USER",nullable = false)
    private User user;

    @Temporal(TemporalType.DATE)
    @Column(name = "ENTRY_DATE",nullable = false)
    private Date entryDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "OUT_DATE",nullable = false)
    private Date outDate;

    @Column(name = "IS_OK",nullable = false)
    private int jobSecOk;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "logincontrol_logincontroldates", joinColumns = @JoinColumn(name = "LOGIN_CONTROL_DATES"))
    private List<LoginControlDates> loginControlDates = new ArrayList<LoginControlDates>();

    @Column(name = "COMPANY_NAME",nullable = false)
    private String companyName;

    @Column(name = "LOGIN_HOUR",nullable = false)
    @Temporal(TemporalType.TIME)
    private Date loginHour;

    @Column(name = "OUT_HOUR",nullable = false)
    @Temporal(TemporalType.TIME)
    private Date outHour;

    @Column(name = "LOGIN_PERMISSION_ID",nullable = false)
    private int loginPermissionId;

    public int getLoginContolId() {
        return loginContolId;
    }

    public void setLoginContolId(int loginContolId) {
        this.loginContolId = loginContolId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LoginControlDates> getLoginControlDates() {
        return loginControlDates;
    }

    public void setLoginControlDates(List<LoginControlDates> loginControlDates) {
        this.loginControlDates = loginControlDates;
    }

    public void addControlDate(LoginControlDates loginControlDates){
        this.loginControlDates.add(loginControlDates);
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public int getJobSecOk() {
        return jobSecOk;
    }

    public void setJobSecOk(int jobSecOk) {
        this.jobSecOk = jobSecOk;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getLoginHour() {
        return loginHour;
    }

    public void setLoginHour(Date loginHour) {
        this.loginHour = loginHour;
    }

    public Date getOutHour() {
        return outHour;
    }

    public void setOutHour(Date outHour) {
        this.outHour = outHour;
    }

    public int getLoginPermissionId() {
        return loginPermissionId;
    }

    public void setLoginPermissionId(int loginPermissionId) {
        this.loginPermissionId = loginPermissionId;
    }
}


