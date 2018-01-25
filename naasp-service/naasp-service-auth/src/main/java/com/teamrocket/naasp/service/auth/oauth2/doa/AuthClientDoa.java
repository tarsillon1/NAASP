package com.teamrocket.naasp.service.auth.oauth2.doa;

import com.teamrocket.naasp.service.auth.oauth2.model.AuthClient;
import com.teamrocket.naasp.service.commons.doa.IGenericDoa;

import java.util.Collection;

/**
 * DOA interface for auth clients.
 * Extra methods for non-generic AuthClient queries.
 */
public interface AuthClientDoa extends IGenericDoa<AuthClient, String> {
    /**
     * Gets all auth clients.
     * @return all the auth clients as a collection
     */
    Collection <AuthClient> getAll();
}