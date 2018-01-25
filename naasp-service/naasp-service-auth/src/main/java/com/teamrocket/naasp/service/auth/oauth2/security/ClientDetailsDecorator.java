package com.teamrocket.naasp.service.auth.oauth2.security;

import com.teamrocket.naasp.service.auth.oauth2.model.AuthClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Decorate a auth client object to support Client Details functions.
 * Client Details are used for oauth2 authentication.
 */
public class ClientDetailsDecorator implements ClientDetails {
    private AuthClient authClient;

    public ClientDetailsDecorator(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public boolean isScoped() {
        return authClient.getScope() != null && !authClient.getScope().isEmpty();
    }

    @Override
    public Set<String> getScope() {
        return authClient.getScope();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authClient.getAuthorizedGrantTypes();
    }

    @Override
    public String getClientId() {
        return authClient.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        if (authClient == null) {

        }

        return authClient.getResourceIds();
    }

    @Override
    public boolean isSecretRequired() {
        return authClient.getClientSecret() != null;
    }

    @Override
    public String getClientSecret() {
        return authClient.getClientSecret();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return authClient.getRegisteredRedirectUris();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>(authClient.getAuthorities());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return authClient.getAccessTokenValiditySeconds();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return authClient.getRefreshTokenValiditySeconds();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (authClient.getAutoApproveScopes() == null) {
            return false;
        }
        for (String auto : authClient.getAutoApproveScopes()) {
            if (auto.equals("true") || scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return authClient.getAdditionalInformation();
    }
}
