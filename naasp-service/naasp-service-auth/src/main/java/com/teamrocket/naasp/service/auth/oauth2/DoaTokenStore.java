package com.teamrocket.naasp.service.auth.oauth2;

import com.teamrocket.naasp.service.auth.oauth2.doa.AccessTokenDoa;
import com.teamrocket.naasp.service.auth.oauth2.doa.RefreshTokenDoa;
import com.teamrocket.naasp.service.auth.oauth2.model.AccessToken;
import com.teamrocket.naasp.service.auth.oauth2.model.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * DOA implementation of a token store.
 */
@Component
public class DoaTokenStore implements TokenStore {
    private final AccessTokenDoa accessTokenDoa;
    private final RefreshTokenDoa refreshTokenDoa;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Autowired
    public DoaTokenStore(final AccessTokenDoa accessTokenDoa,
                         final RefreshTokenDoa refreshTokenDoa) {
        this.accessTokenDoa = accessTokenDoa;
        this.refreshTokenDoa = refreshTokenDoa;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(extractTokenKey(token.getValue()));
    }

    @Override
    public OAuth2Authentication readAuthentication(String tokenId) {
        return deserializeAuthentication(accessTokenDoa.get(tokenId).getAuthentication());
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String tokenKey = extractTokenKey(token.getValue());
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }

        AccessToken accessToken = new AccessToken(tokenKey,
                serializeAccessToken(token),
                authenticationKeyGenerator.extractKey(authentication),
                authentication.isClientOnly() ? null : authentication.getName(),
                authentication.getOAuth2Request().getClientId(),
                serializeAuthentication(authentication),
                extractTokenKey(refreshToken));

        accessTokenDoa.create(accessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        AccessToken token = accessTokenDoa.get(extractTokenKey(tokenValue));
        if(token == null) {
            return null;
        }
        OAuth2AccessToken accessToken = deserializeAccessToken(token.getToken());
		return accessToken;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        String tokenKey = extractTokenKey(token.getValue());
        AccessToken accessToken = accessTokenDoa.get(tokenKey);
        if(accessToken != null) {
            accessTokenDoa.delete(tokenKey);
        }
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        String tokenKey = extractTokenKey(refreshToken.getValue());
        refreshTokenDoa.create(new RefreshToken(tokenKey,
                serializeRefreshToken(refreshToken),
                serializeAuthentication(authentication)));
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return deserializeRefreshToken(refreshTokenDoa.get(extractTokenKey(tokenValue)).getToken());
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return deserializeAuthentication(refreshTokenDoa.get(extractTokenKey(token.getValue())).getAuthentication());
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        refreshTokenDoa.delete(extractTokenKey(token.getValue()));
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        accessTokenDoa.delete(extractTokenKey(refreshToken.getValue()));
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        AccessToken token =  accessTokenDoa.findByAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        return token == null ? null : deserializeAccessToken(token.getToken());
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        Collection<AccessToken> tokens = accessTokenDoa.findByClientId(clientId);
        return extractAccessTokens(tokens);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        Collection<AccessToken> tokens = accessTokenDoa.findByClientIdAndUserName(clientId, userName);
        return extractAccessTokens(tokens);
    }

    protected byte[] serializeAccessToken(OAuth2AccessToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
        return SerializationUtils.serialize(authentication);
    }

    protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
        return SerializationUtils.deserialize(token);
    }

    protected OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
        return SerializationUtils.deserialize(token);
    }

    protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
        return SerializationUtils.deserialize(authentication);
    }

    private Collection<OAuth2AccessToken> extractAccessTokens(Collection<AccessToken> tokens) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<>();
        for(AccessToken token : tokens) {
            accessTokens.add(deserializeAccessToken(token.getToken()));
        }
        return accessTokens;
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }

}