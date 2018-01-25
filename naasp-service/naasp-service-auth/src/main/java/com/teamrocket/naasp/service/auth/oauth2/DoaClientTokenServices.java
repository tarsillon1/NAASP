package com.teamrocket.naasp.service.auth.oauth2;

import com.teamrocket.naasp.service.auth.oauth2.doa.ClientTokenDoa;
import com.teamrocket.naasp.service.auth.oauth2.model.ClientToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientKeyGenerator;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * DOA implementation of a client token service.
 */
@Component
public class DoaClientTokenServices implements ClientTokenServices {
    private ClientTokenDoa clientTokenDoa;
    private ClientKeyGenerator clientKeyGenerator;

    @Autowired
    public DoaClientTokenServices(ClientTokenDoa clientTokenDoa,
                                  ClientKeyGenerator clientKeyGenerator) {
        this.clientTokenDoa = clientTokenDoa;
        this.clientKeyGenerator = clientKeyGenerator;
    }

    @Override
    public OAuth2AccessToken getAccessToken(final OAuth2ProtectedResourceDetails resource,
                                            final Authentication authentication) {
        ClientToken clientToken = clientTokenDoa.findByAuthenticationId(
                clientKeyGenerator.extractKey(resource, authentication));
        return SerializationUtils.deserialize(clientToken.getToken());
    }

    @Override
    public void saveAccessToken(final OAuth2ProtectedResourceDetails resource,
                                final Authentication authentication,
                                final OAuth2AccessToken accessToken) {
        removeAccessToken(resource, authentication);
        ClientToken clientToken = new ClientToken(UUID.randomUUID().toString(),
                accessToken.getValue(),
                SerializationUtils.serialize(accessToken),
                clientKeyGenerator.extractKey(resource, authentication),
                authentication.getName(),
                resource.getClientId());

        if (clientTokenDoa.get(clientToken.getId()) == null) {
            clientTokenDoa.create(clientToken);
        } else {
            clientTokenDoa.update(clientToken.getId(), clientToken);
        }
    }

    @Override
    public void removeAccessToken(final OAuth2ProtectedResourceDetails resource,
                                  final Authentication authentication) {
        clientTokenDoa.deleteByAuthenticationId(clientKeyGenerator.extractKey(resource, authentication));
    }
}