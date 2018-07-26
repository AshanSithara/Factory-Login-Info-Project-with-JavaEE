package com.proje.dto;

import com.proje.model.LoginControl;
import com.proje.model.LoginControlDates;
import com.proje.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginControlDto {

    private int loginContolId;
    private User user;
    private Date entryDate;
    private Date outDate;
    private int jobSecOk;
    private List<LoginControlDatesDto> loginControlDates = new ArrayList<LoginControlDatesDto>();
    private String companyName;
    private Date loginHour;
    private Date outHour;

    public LoginControlDto(){
    }

    public LoginControlDto(LoginControl loginControl) {
        this.loginContolId = loginControl.getLoginContolId();
        this.user = loginControl.getUser();
    }

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

    public List<LoginControlDatesDto> getLoginControlDates() {
        return loginControlDates;
    }

    public void setLoginControlDates(List<LoginControlDatesDto> loginControlDates) {
        this.loginControlDates = loginControlDates;
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

    public void addControlDate(LoginControlDatesDto loginControlDates){
        this.loginControlDates.add(loginControlDates);
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
}
