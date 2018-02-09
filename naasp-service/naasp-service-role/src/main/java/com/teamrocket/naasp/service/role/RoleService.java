package com.teamrocket.naasp.service.role;

import com.teamrocket.naasp.service.auth.AuthService;
import com.teamrocket.naasp.service.auth.oauth2.model.AuthClient;
import com.teamrocket.naasp.service.auth.user.AuthUser;
import com.teamrocket.naasp.service.role.exception.RoleNotFoundException;
import com.teamrocket.naasp.service.role.model.DefaultRole;
import com.teamrocket.naasp.service.role.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service handles adding, removing and checking user/client roles.
 */
@Service
public class RoleService {
    private AuthService authService;

    @Autowired
    public RoleService (AuthService authService) {
        this.authService = authService;
    }

    /**
     * Assign a role to a user, by username.
     * @param username the username
     * @param role the role
     */
    public Role assignRoleByUsername (String username, Role role) {
        if (!currentHasRole(DefaultRole.WRITE_ROLE)) {
            throw new RoleNotFoundException(DefaultRole.WRITE_ROLE);
        }

        AuthUser authUser = authService.getAuthUserByUsername(username);
        authUser.getRoles().add(role.getRole());
        authService.updateAuthUser(authUser);
        return role;
    }

    /**
     * Assign a role to a user, by user id.
     * @param userId the user id
     * @param role the role
     */
    public Role assignRoleByUserId (String userId, Role role) {
        if (!currentHasRole(DefaultRole.WRITE_ROLE)) {
            throw new RoleNotFoundException(DefaultRole.WRITE_ROLE);
        }

        AuthUser authUser = authService.getAuthUserByUserId(userId);
        authUser.getRoles().add(role.getRole());
        authService.updateAuthUser(authUser);
        return role;
    }

    /**
     * Assign a role to a client, by client id.
     * @param clientId the client id
     * @param role the role
     */
    public Role assignRoleByClientId (String clientId, Role role) {
        if (!currentHasRole(DefaultRole.WRITE_ROLE)) {
            throw new RoleNotFoundException(DefaultRole.WRITE_ROLE);
        }

        AuthClient authClient = authService.getAuthClientByClientId(clientId);
        authClient.getAuthorities().add(role.getRole());
        authService.updateAuthClient(authClient);
        return role;
    }

    /**
     * Checks if a user has a role, by username.
     * @param username the username
     * @param role the role
     */
    public boolean hasRoleByUsername (String username, Role role) {
        if (!currentHasRole(DefaultRole.READ_ROLE)) {
            throw new RoleNotFoundException(DefaultRole.READ_ROLE);
        }

        AuthUser authUser = authService.getAuthUserByUsername(username);
        return authUser.getRoles().contains(role.getRole());
    }

    /**
     * Checks if a user has a role, by user id.
     * @param userId the user id
     * @param role the role
     */
    public boolean hasRoleByUserId (String userId, Role role) {
        if (!currentHasRole(DefaultRole.READ_ROLE)) {
            throw new RoleNotFoundException(DefaultRole.READ_ROLE);
        }

        AuthUser authUser = authService.getAuthUserByUserId(userId);
        return authUser.getRoles().contains(role.getRole());
    }

    /**
     * Checks if a client has a role, by client id.
     * @param clientId the client id
     * @param role the role
     */
    public boolean hasRoleByClientId (String clientId, Role role) {
        if (!currentHasRole(DefaultRole.READ_ROLE)) {
            throw new RoleNotFoundException(DefaultRole.READ_ROLE);
        }

        AuthClient authClient = authService.getAuthClientByClientId(clientId);
        return authClient.getAuthorities().contains(role.getRole());
    }

    /**
     * Revokes a role, by username.
     * @param username the username
     * @param role the role
     */
    public Role revokeRoleByUsername (String username, Role role) {
        if (!currentHasRole(DefaultRole.WRITE_ROLE)) {
            throw new RoleNotFoundException(DefaultRole.WRITE_ROLE);
        }

        AuthUser authUser = authService.getAuthUserByUsername(username);
        if (authUser.getRoles().contains(role.getRole())) {
            authUser.getRoles().remove(role.getRole());
        }
        authService.updateAuthUser(authUser);
        return role;
    }


    public boolean currentHasRole (Role role) {
        return authService.hasAuthority(role.getRole());
    }

    /**
     * Revokes a role, by user id.
     * @param userId the userId
     * @param role the role
     */
    public Role revokeRoleByUserId (String userId, Role role) {
        if (!currentHasRole(DefaultRole.WRITE_ROLE)) {
            throw new RoleNotFoundException(DefaultRole.WRITE_ROLE);
        }

        AuthUser authUser = authService.getAuthUserByUserId(userId);
        if (authUser.getRoles().contains(role.getRole())) {
            authUser.getRoles().remove(role.getRole());
        }
        authService.updateAuthUser(authUser);
        return role;
    }

    /**
     * Revokes a role, by client id.
     * @param clientId the client id
     * @param role the role
     */
    public Role revokeRoleByClientId (String clientId, Role role) {
        if (!currentHasRole(DefaultRole.WRITE_ROLE)) {
            throw new RoleNotFoundException(DefaultRole.WRITE_ROLE);
        }

        AuthClient authClient = authService.getAuthClientByClientId(clientId);
        if (authClient.getAuthorities().contains(role.getRole())) {
            authClient.getAuthorities().remove(role.getRole());
        }
        authService.updateAuthClient(authClient);
        return role;
    }

}
