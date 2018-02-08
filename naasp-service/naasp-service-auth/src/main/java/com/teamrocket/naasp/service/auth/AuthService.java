package com.teamrocket.naasp.service.auth;

import com.teamrocket.naasp.service.auth.oauth2.doa.AuthClientDoa;
import com.teamrocket.naasp.service.auth.oauth2.model.AuthClient;
import com.teamrocket.naasp.service.auth.oauth2.security.SecurityContextService;
import com.teamrocket.naasp.service.auth.user.AuthUser;
import com.teamrocket.naasp.service.auth.user.doa.AuthUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The auth service is the base service of our system.
 * It handles all authentication functions and data.
 */
@Service
public class AuthService {
    private AuthUserDao authUserDao;
    private AuthClientDoa authClientDoa;
    private SecurityContextService securityContextService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthUserDao authUserDao, AuthClientDoa authClientDoa,
                       SecurityContextService securityContextService, PasswordEncoder passwordEncoder) {
        this.authUserDao = authUserDao;
        this.authClientDoa = authClientDoa;
        this.securityContextService = securityContextService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthUser getAuthUserByUsername (String username) {
        return authUserDao.get(username);
    }

    public AuthUser getAuthUserByUserId (String userId) {
        return authUserDao.findAuthUserByUserId(userId);
    }

    public AuthUser updateAuthUser (AuthUser authUser) {
        return authUserDao.update(authUser);
    }

    public AuthUser createAuthUser (AuthUser authUser, boolean encodePassword) {
        if (encodePassword) {
            authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        }
        return authUserDao.create(authUser);
    }

    public AuthClient createAuthClient (AuthClient authClient, boolean encodeSecret) {
        if (encodeSecret) {
            authClient.setClientSecret(passwordEncoder.encode(authClient.getClientSecret()));
        }
        return authClientDoa.create(authClient);
    }

    public AuthClient getAuthClientByClientId (String clientId) {
        return authClientDoa.get(clientId);
    }

    public AuthClient updateAuthClient (AuthClient authClient) {
        return authClientDoa.update(authClient);
    }

    public boolean hasAuthority (String authority) {
        return securityContextService.getAuthentication().getAuthorities().contains(authority);
    }
}
