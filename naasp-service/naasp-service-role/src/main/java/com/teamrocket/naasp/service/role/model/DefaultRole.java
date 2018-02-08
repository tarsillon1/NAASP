package com.teamrocket.naasp.service.role.model;

public enum DefaultRole implements Role {
    WRITE_ROLE,
    READ_ROLE;

    @Override
    public String getRole() {
        return this.toString();
    }
}
