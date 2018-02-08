package com.teamrocket.naasp.service.company.model;

import org.springframework.data.annotation.Id;

public class Company {
    @Id
    private String companyId;
    private CompanyData companyData;
    private CompanySettings companySettings;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public CompanyData getCompanyData() {
        return companyData;
    }

    public void setCompanyData(CompanyData companyData) {
        this.companyData = companyData;
    }

    public CompanySettings getCompanySettings() {
        return companySettings;
    }

    public void setCompanySettings(CompanySettings companySettings) {
        this.companySettings = companySettings;
    }
}
