package com.teamrocket.naasp.service.auth.security;


import com.teamrocket.naasp.service.auth.user.AuthUser;
import com.teamrocket.naasp.service.auth.user.doa.AuthUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.token.ClientKeyGenerator;
import org.springframework.security.oauth2.client.token.DefaultClientKeyGenerator;
import org.springframework.stereotype.Component;

@Component
public class AuthUserAuthenticationManager implements AuthenticationManager {
    private AuthUserDao authUserDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthUserAuthenticationManager (AuthUserDao authUserDao) {
        this.authUserDao = authUserDao;
        this.passwordEncoder = passwordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        AuthUser authUser = authUserDao.get(username);
        if (authUser == null) {
            throw new BadCredentialsException("1000");
        }
        if (!authUser.isEnabled()) {
            throw new DisabledException("1001");
        }

        if (!passwordEncoder.matches(password, authUser.getPassword())) {
            throw new BadCredentialsException("1000");
        }

        return new UsernamePasswordAuthenticationToken(username, password, new UserDetailsDecorator(authUser).getAuthorities());
    }

    @Primary
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClientKeyGenerator clientKeyGenerator() {
        return new DefaultClientKeyGenerator();
    }
}
