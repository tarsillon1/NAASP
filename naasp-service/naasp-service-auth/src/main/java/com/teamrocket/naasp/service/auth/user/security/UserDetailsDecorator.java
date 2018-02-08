package com.teamrocket.naasp.service.auth.user.security;

import com.teamrocket.naasp.service.auth.user.AuthUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsDecorator implements UserDetails {
    private AuthUser authUser;

    public UserDetailsDecorator(AuthUser authUser) {
        this.authUser = authUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String val: authUser.getRoles()) {
            grantedAuthorities.add(() -> val);
        }

        return new ArrayList<>(grantedAuthorities);
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