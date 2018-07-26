package com.proje.model;

import javax.persistence.*;

@Entity
@Table(name = "company")
@NamedQueries({
        @NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c"),
        @NamedQuery(name = "Company.findCompanyWithId", query = "SELECT c FROM Company c WHERE c.companyId = :companyId")
})
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_ID")
    private int companyId;

    @Column(name = "COMPANY_NAME",unique = true,nullable = false)
    private String companyName;

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
