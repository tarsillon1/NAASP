package com.teamrocket.naasp.service.auth.oauth2.doa;

import com.teamrocket.naasp.service.auth.oauth2.model.AccessToken;
import com.teamrocket.naasp.service.commons.doa.IGenericDoa;

import java.util.Collection;

/**
 * DOA interface for access tokens.
 * Extra methods for non-generic Access Token queries.
 */
public interface AccessTokenDoa extends IGenericDoa<AccessToken, String> {
    /**
     * Finds a access token by refresh token.
     * @param refreshToken the refresh token.
     * @return the access token
     */
    AccessToken findByRefreshToken(String refreshToken);

    /**
     * Finds a access token by authentication id.
     * @param authenticationId the authentication id.
     * @return the access token
     */
    AccessToken findByAuthenticationId(String authenticationId);

    /**
     * Finds all access tokens under a specific client id and username.
     * @param clientId the client id
     * @param userName the username
     * @return the access tokens as list
     */
    Collection<AccessToken> findByClientIdAndUserName(String clientId, String userName);

    /**
     * Finds all clients with a specific client id.
     * @param clientId the client id
     * @return the access tokens as list
     */
    Collection<AccessToken> findByClientId(String clientId);
}