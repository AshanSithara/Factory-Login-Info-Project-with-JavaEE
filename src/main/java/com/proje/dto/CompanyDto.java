package com.proje.dto;

import com.proje.model.Company;

public class CompanyDto {

    private int companyId;
    private String companyName;

    public CompanyDto(){
    }

    public CompanyDto(Company company){
        this.companyId = company.getCompanyId();
        this.companyName = company.getCompanyName();
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
