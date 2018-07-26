package com.proje.dto;

import com.proje.model.IntroductionReason;
import com.proje.model.LoginPermission;

import java.util.ArrayList;
import java.util.List;

public class IntroductionReasonDto {

    private int reasonId;
    private String reasonName;
    private List<LoginPermission> loginPermissions = new ArrayList<LoginPermission>();

    public IntroductionReasonDto(){
    }

    public IntroductionReasonDto(IntroductionReason introductionReason){
        this.reasonId = introductionReason.getReasonId();
        this.reasonName = introductionReason.getReasonName();
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public List<LoginPermission> getLoginPermissions() {
        return loginPermissions;
    }

    public void setLoginPermissions(List<LoginPermission> loginPermissions) {
        this.loginPermissions = loginPermissions;
    }
}
