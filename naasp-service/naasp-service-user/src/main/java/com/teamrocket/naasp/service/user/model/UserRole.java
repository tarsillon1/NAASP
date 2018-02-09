package com.teamrocket.naasp.service.user.model;

import com.teamrocket.naasp.service.role.model.Role;

/**
 * Permissions for user service.
 */
public enum UserRole implements Role {
    USER_READ, USER_WRITE;

    @Override
    public String getRole() {
        return toString();
    }
}
