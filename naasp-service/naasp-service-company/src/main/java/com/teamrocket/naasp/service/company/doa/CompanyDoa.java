package com.teamrocket.naasp.service.company.doa;

import com.teamrocket.naasp.service.commons.doa.IGenericDoa;
import com.teamrocket.naasp.service.company.model.Company;

public interface CompanyDoa extends IGenericDoa <Company, String> {
    Company findCompanyByName(String companyName);
}
