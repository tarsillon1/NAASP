package com.teamrocket.naasp.service.auth.oauth2;

import com.teamrocket.naasp.service.auth.oauth2.doa.AuthClientDoa;
import com.teamrocket.naasp.service.auth.oauth2.model.AuthClient;
import com.teamrocket.naasp.service.auth.oauth2.security.ClientDetailsDecorator;
import com.teamrocket.naasp.service.auth.user.AuthRole;
import com.teamrocket.naasp.service.commons.doa.exception.DuplicateObjectException;
import com.teamrocket.naasp.service.commons.doa.exception.GetObjectException;
import com.teamrocket.naasp.service.commons.doa.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

/**
 * DOA implementation of an approval store.
 */
@Component
public class DoaClientDetailsService implements ClientDetailsService, ClientRegistrationService {
    private AuthClientDoa authClientDoa;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public DoaClientDetailsService(final AuthClientDoa authClientDoa,
                                   final PasswordEncoder passwordEncoder) {
        this.authClientDoa = authClientDoa;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        try {
            AuthClient authClient = authClientDoa.get(clientId);
            if (authClient == null) {
                throw new ClientRegistrationException("No Client Details for client id");
            }

            return new ClientDetailsDecorator(authClient);
        } catch (GetObjectException e) {
            throw new ClientRegistrationException("No Client Details for client id", e);
        } catch (Exception e) {
            throw new ClientRegistrationException("Internal error occurred.", e);
        }
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        try {
            authClientDoa.create(toAuthClient(clientDetails));
        } catch (DuplicateObjectException e) {
            throw new ClientAlreadyExistsException ("Client already exists!");
        }
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        try {
            AuthClient authClient = toAuthClient(clientDetails);
            authClientDoa.update(authClient.getClientId(), authClient);
        } catch (ObjectNotFoundException e) {
            throw new NoSuchClientException ("Client does not exist!");
        }
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        AuthClient authClient = authClientDoa.get(clientId);

        if (authClient == null) {
            throw new NoSuchClientException("Client does not exist!");
        }

        authClient.setClientSecret(passwordEncoder.encode(secret));
        authClientDoa.update(authClient.getClientId(), authClient);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        authClientDoa.delete(clientId);
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        List<AuthClient> all = newArrayList(authClientDoa.getAll());
        return toClientDetails(all);
    }

    private List<ClientDetails> toClientDetails (Collection<AuthClient> authClients) {
        List<ClientDetails> clientDetails = new ArrayList<>();
        for (AuthClient authClient : authClients) {
            clientDetails.add(new ClientDetailsDecorator(authClient));
        }

        return clientDetails;
    }

    private AuthClient toAuthClient (ClientDetails clientDetails) {
        return new AuthClient(
                clientDetails.getClientId(),
                passwordEncoder.encode(clientDetails.getClientSecret()),
                clientDetails.getScope(),
                clientDetails.getResourceIds(),
                clientDetails.getAuthorizedGrantTypes(),
                clientDetails.getRegisteredRedirectUri(),
                newArrayList(AuthRole.getRoles(clientDetails.getAuthorities())),
                clientDetails.getAccessTokenValiditySeconds(),
                clientDetails.getRefreshTokenValiditySeconds(),
                clientDetails.getAdditionalInformation(),
                getAutoApproveScopes(clientDetails));

    }

    private Set<String> getAutoApproveScopes(final ClientDetails clientDetails) {
        if (clientDetails.isAutoApprove("true")) {
            return new HashSet<>(Arrays.asList(new String[]{"true"}));
        }

        return clientDetails.getScope().stream()
                .filter(scope -> clientDetails.isAutoApprove(scope)).collect(Collectors.toSet());
    }
}