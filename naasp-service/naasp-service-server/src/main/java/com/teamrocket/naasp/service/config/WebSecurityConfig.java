package com.teamrocket.naasp.service.config;

import com.teamrocket.naasp.service.auth.security.AuthUserAuthenticationManager;
import com.teamrocket.naasp.service.auth.security.AuthUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Enables this server to require oauth2 sso authentication.
 * Configures paths that do not require authentication.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthUserAuthenticationManager authenticationManager;
    @Autowired
    private AuthUserDetailsManager userDetailsManager;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v1/healthcheck");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/login", "/oauth/authorize")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(authenticationManager)
                .userDetailsService(userDetailsManager);
    }
}
