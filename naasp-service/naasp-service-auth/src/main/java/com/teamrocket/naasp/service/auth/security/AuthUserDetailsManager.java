package com.teamrocket.naasp.service.auth.security;

import com.teamrocket.naasp.service.auth.oauth2.security.SecurityContextService;
import com.teamrocket.naasp.service.auth.user.AuthRole;
import com.teamrocket.naasp.service.auth.user.AuthUser;
import com.teamrocket.naasp.service.auth.user.doa.AuthUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.UUID;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Component
public class AuthUserDetailsManager implements UserDetailsManager {
    private static final Logger LOG = LoggerFactory.getLogger(AuthUserDetailsManager.class);

    private AuthUserDao authUserDao;
    private AuthUserAuthenticationManager authenticationManager;
    private SecurityContextService securityContextService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthUserDetailsManager(AuthUserDao authUserDao,
                                  AuthUserAuthenticationManager authenticationManager,
                                  SecurityContextService securityContextService,
                                  PasswordEncoder passwordEncoder) {
        this.authUserDao = authUserDao;
        this.authenticationManager = authenticationManager;
        this.securityContextService = securityContextService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDetails user) {
        validateUserDetails(user);
        authUserDao.create(getUser(user));
    }

    @Override
    public void updateUser(UserDetails user) {
        validateUserDetails(user);
        authUserDao.update(user.getUsername(), getUser(user));
    }

    @Override
    public void deleteUser(String username) {
        authUserDao.delete(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = securityContextService.getAuthentication();

        if (currentUser == null) {
            throw new AccessDeniedException("Can't change password as no Authentication object found in context " +
                    "for current user.");
        }

        String username = currentUser.getName();

        if (authenticationManager != null) {
            LOG.debug("Reauthenticating user '"+ username + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            LOG.debug("No authentication manager set. Password won't be re-checked.");
        }

        LOG.debug("Changing password for user '"+ username + "'");

        AuthUser authUser = authUserDao.get(username);
        authUser.setPassword(passwordEncoder.encode(newPassword));
        authUserDao.update(username, authUser);

        securityContextService.setAuthentication(createNewAuthentication(currentUser));
    }

    @Override
    public boolean userExists(String username) {
        return authUserDao.get(username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDao.get(username);
        if (authUser == null) {
            throw new UsernameNotFoundException("Username could not be found.");
        }
        return new UserDetailsDecorator(authUser);
    }

    protected Authentication createNewAuthentication(Authentication currentAuth) {
        final UserDetails user = loadUserByUsername(currentAuth.getName());

        final UsernamePasswordAuthenticationToken newAuthentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        newAuthentication.setDetails(currentAuth.getDetails());

        return newAuthentication;
    }

    private AuthUser getUser(UserDetails userDetails) {
        return new AuthUser(
                userDetails.getPassword(),
                userDetails.getUsername(),
                UUID.randomUUID().toString(),
                newArrayList(AuthRole.getRoles(userDetails.getAuthorities())),
                !userDetails.isAccountNonExpired(),
                !userDetails.isAccountNonLocked(),
                !userDetails.isCredentialsNonExpired(),
                userDetails.isEnabled());
    }

    private void validateUserDetails(UserDetails user) {
        Assert.hasText(user.getUsername(), "Username may not be empty or null");
        validateAuthorities(user.getAuthorities());
    }

    private void validateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Authorities list must not be null");

        for (GrantedAuthority authority : authorities) {
            Assert.notNull(authority, "Authorities list contains a null entry");
            Assert.hasText(authority.getAuthority(), "getAuthority() method must return a non-empty string");
        }
    }
}