package com.teamrocket.naasp.service.auth.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum AuthRole implements GrantedAuthority {
    USER("USER");

    private String role;

    AuthRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public static Collection<AuthRole> getRoles (Collection<? extends GrantedAuthority> authorities) {
        List<AuthRole> authRoles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            authRoles.add(AuthRole.valueOf(authority.getAuthority()));
        }

        return authRoles;
    }
}
