package com.proje.model;

import javax.persistence.*;
import java.util.Date;

@Embeddable
public class LoginControlDates {

    @Temporal(TemporalType.DATE)
    private Date controlDate;

    @Temporal(TemporalType.TIME)
    private Date loginHour;

    @Temporal(TemporalType.TIME)
    private Date outHour;

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
