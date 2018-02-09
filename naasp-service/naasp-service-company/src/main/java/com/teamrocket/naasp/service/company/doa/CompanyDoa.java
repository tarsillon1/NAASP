package com.teamrocket.naasp.service.company.doa;

import com.teamrocket.naasp.service.commons.doa.IGenericDoa;
import com.teamrocket.naasp.service.company.model.Company;

/**
 * A doa for storing and retrieving companies.
 */
public interface CompanyDoa extends IGenericDoa <Company, String> {
    Company findCompanyByName(String companyName);
}
