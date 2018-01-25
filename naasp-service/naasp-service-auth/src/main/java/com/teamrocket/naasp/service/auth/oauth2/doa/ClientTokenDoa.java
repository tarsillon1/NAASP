package com.teamrocket.naasp.service.auth.oauth2.doa;

import com.teamrocket.naasp.service.auth.oauth2.model.ClientToken;
import com.teamrocket.naasp.service.commons.doa.IGenericDoa;

/**
 * DOA interface for client tokens.
 * Extra methods for non-generic ClientToken queries.
 */
public interface ClientTokenDoa extends IGenericDoa<ClientToken, String> {
    /**
     * Deletes a client token by authentication id.
     * If multiple exist all will be deleted. (This would indicate an error.)
     * @param authenticationId the authentication id
     * @return the deleted client token
     */
    ClientToken deleteByAuthenticationId(String authenticationId);

    /**
     * Finds a client token by authentication id.
     * @param authenticationId the authentication id
     * @return the client token
     */
    ClientToken findByAuthenticationId(String authenticationId);
}