package com.teamrocket.naasp.service.auth;

import com.teamrocket.naasp.service.auth.oauth2.doa.AuthClientDoa;
import com.teamrocket.naasp.service.auth.oauth2.model.AuthClient;
import com.teamrocket.naasp.service.auth.user.AuthUser;
import com.teamrocket.naasp.service.auth.user.doa.AuthUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class AuthService {
    private AuthUserDao authUserDao;
    private AuthClientDoa authClientDoa;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthUserDao authUserDao, AuthClientDoa authClientDoa, PasswordEncoder passwordEncoder) {
        this.authUserDao = authUserDao;
        this.authClientDoa = authClientDoa;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthUser createAuthUser (AuthUser authUser) {
        AuthClient authClient = new AuthClient();
        authClient.setClientSecret(authUser.getPassword());
        authClient.setClientId(authUser.getUserName());
        authClient.setAuthorities(authUser.getRoles());
        authClient.setScope(new HashSet<>(Arrays.asList(new String[]{"true"})));
        authClient.setAutoApproveScopes(new HashSet<>(Arrays.asList(new String[]{"true"})));
        authClientDoa.create(authClient);
        return authUserDao.create(authUser);
    }
}
