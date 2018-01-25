package com.teamrocket.naasp.service.auth.oauth2.doa;

import com.teamrocket.naasp.service.auth.oauth2.model.AuthApproval;
import com.teamrocket.naasp.service.commons.doa.IGenericDoa;

import java.util.Collection;

/**
 * DOA interface for AuthApprovals.
 * Extra methods for non-generic AuthApproval queries.
 */
public interface AuthApprovalDoa extends IGenericDoa<AuthApproval, String> {
    /**
     * Finds all AuthApprovals with a specific user id and client id.
     * @param userId the user id
     * @param clientId the client id
     * @return the auth approvals as a collection
     */
    Collection<AuthApproval> findByUserIdAndClientId(String userId, String clientId);

    /**
     * Updates or creates a collection of auth approvals
     * @param authApprovals the auth approvals to create or update
     * @return the auth approvals as a collection
     */
    Collection<AuthApproval> updateOrCreate(Collection<AuthApproval> authApprovals);
}