package com.teamrocket.naasp.service.auth.oauth2.model;

import org.springframework.data.annotation.Id;

/**
 * A data object that represents and oauth2 sso refresh token.
 *
 * Whenever an access token is required to access a specific resource,
 * a client may use a refresh token to get a new access token issued by
 * the authentication server.
 */
public class RefreshToken {
    @Id
    private String tokenId;
    private byte[] token;
    private byte[] authentication;

    public RefreshToken() {}

    public RefreshToken(String tokenId,
                        byte[] token,
                        byte[] authentication) {
        this.tokenId = tokenId;
        this.token = token;
        this.authentication = authentication;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }
}