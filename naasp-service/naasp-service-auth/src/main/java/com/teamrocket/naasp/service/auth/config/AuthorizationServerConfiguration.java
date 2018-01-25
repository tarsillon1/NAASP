package com.teamrocket.naasp.service.auth.config;

import com.teamrocket.naasp.service.auth.oauth2.DoaApprovalStore;
import com.teamrocket.naasp.service.auth.oauth2.DoaClientDetailsService;
import com.teamrocket.naasp.service.auth.oauth2.DoaTokenStore;
import com.teamrocket.naasp.service.auth.security.AuthUserAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

/**
 * Authorization server configuration for oauth2 SSO,
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private DoaTokenStore tokenStore;

    @Autowired
    private DoaClientDetailsService clientDetailsService;

    @Autowired
    private DoaApprovalStore approvalStore;

    @Autowired
    private AuthUserAuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Configure the server for token authentication.
     * Configure password encoder for server.
     * @param oauthServer the server
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.passwordEncoder(passwordEncoder)
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * Configure the client details service to a DOA based implementation.
     * @param clients the client details service configurer
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * Configure resource server endpoints to use oauth2 sso authentication.
     * @param endpoints the endpoint configurer
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
        .approvalStore(approvalStore).tokenStore(tokenStore);
    }

    /**
     * Create token services for oauth2 sso.
     * @return the token services
     */
    @Primary
    @Bean
    public DefaultTokenServices tokenService() {
        DefaultTokenServices tokenService = new DefaultTokenServices();
        tokenService.setClientDetailsService(clientDetailsService);
        tokenService.setTokenStore(tokenStore);
        tokenService.setAuthenticationManager(authenticationManager);
        tokenService.setSupportRefreshToken(true);
        return tokenService;
    }
}