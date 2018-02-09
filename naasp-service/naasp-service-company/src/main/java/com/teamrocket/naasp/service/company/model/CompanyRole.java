package com.teamrocket.naasp.service.company.model;

import com.teamrocket.naasp.service.role.model.Role;

/**
 * Permissions for company service.
 */
public enum CompanyRole implements Role {
    READ_COMPANY,
    WRITE_COMPANY;

    @Override
    public String getRole() {
        return toString();
    }
}
