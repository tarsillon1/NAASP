package com.teamrocket.naasp.service.company;

import com.teamrocket.naasp.service.company.doa.CompanyDoa;
import com.teamrocket.naasp.service.company.model.Company;
import com.teamrocket.naasp.service.company.model.CompanyRole;
import com.teamrocket.naasp.service.role.RoleService;
import com.teamrocket.naasp.service.role.exception.RoleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private CompanyDoa companyDoa;

    public Company getCompanyById (String companyId) {
        if (roleService.currentHasRole(CompanyRole.READ_COMPANY)) {
            throw new RoleNotFoundException(CompanyRole.READ_COMPANY);
        }

        return companyDoa.get(companyId);
    }

    public Company getCompanyByName (String companyName) {
        if (roleService.currentHasRole(CompanyRole.READ_COMPANY)) {
            throw new RoleNotFoundException(CompanyRole.READ_COMPANY);
        }

        return companyDoa.findCompanyByName(companyName);
    }

    public Company createCompany (Company company) {
        if (roleService.currentHasRole(CompanyRole.WRITE_COMPANY)) {
            throw new RoleNotFoundException(CompanyRole.WRITE_COMPANY);
        }

        return companyDoa.create(company);
    }

    public Company updateCompany (Company company) {
        if (roleService.currentHasRole(CompanyRole.WRITE_COMPANY)) {
            throw new RoleNotFoundException(CompanyRole.WRITE_COMPANY);
        }

        return companyDoa.update(company);
    }
}
