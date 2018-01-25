package com.teamrocket.naasp.service.auth.oauth2.model;

import org.springframework.data.annotation.Id;

/**
 * A data object that represents and oauth2 sso client token.
 */
public class ClientToken {
    @Id
    private String id;
    private String tokenId;
    private byte[] token;
    private String authenticationId;
    private String username;
    private String clientId;

    public ClientToken() {}

    public ClientToken(String id,
                       String tokenId,
                       byte[] token,
                       String authenticationId,
                       String username,
                       String clientId) {
        this.id = id;
        this.tokenId = tokenId;
        this.token = token;
        this.authenticationId = authenticationId;
        this.username = username;
        this.clientId = clientId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public String getUsername() {
        return username;
    }

    public String getClientId() {
        return clientId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
