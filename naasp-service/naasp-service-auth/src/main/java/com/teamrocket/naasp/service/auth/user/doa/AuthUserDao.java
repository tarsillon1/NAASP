package com.teamrocket.naasp.service.auth.user.doa;

import com.teamrocket.naasp.service.auth.user.AuthUser;
import com.teamrocket.naasp.service.commons.doa.IGenericDoa;

public interface AuthUserDao extends IGenericDoa<AuthUser, String> {
    /**
     * Find a auth user by a given user id.
     * @return the auth user
     */
    AuthUser findAuthUserByUserId(String userId);
}
