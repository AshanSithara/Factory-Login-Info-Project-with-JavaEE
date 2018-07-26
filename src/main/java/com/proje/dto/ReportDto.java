package com.proje.dto;

import java.util.Date;

public class ReportDto {

    private String personelName;
    private Date controlDate;
    private Date loginHour;
    private Date outHour;

    public String getPersonelName() {
        return personelName;
    }

    public void setPersonelName(String personelName) {
        this.personelName = personelName;
    }

    public Date getControlDate() {
        return controlDate;
    }

    public void setControlDate(Date controlDate) {
        this.controlDate = controlDate;
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
