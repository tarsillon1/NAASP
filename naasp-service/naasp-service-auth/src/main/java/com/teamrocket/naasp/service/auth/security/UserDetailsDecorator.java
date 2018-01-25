package com.teamrocket.naasp.service.auth.security;

import com.teamrocket.naasp.service.auth.user.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsDecorator implements UserDetails {
    private AuthUser authUser;

    public UserDetailsDecorator(AuthUser authUser) {
        this.authUser = authUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authUser.getRoles();
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !authUser.isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !authUser.isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !authUser.isCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return authUser.isEnabled();
    }

}