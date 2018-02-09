package com.teamrocket.naasp.service.role.exception;

import com.teamrocket.naasp.service.role.model.Role;

/**
 * Exception thrown when a required role is not found.
 */
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(Role role) {
        super ("Unauthorized. Authorization does not have required role: " + role.getRole());
    }
}
