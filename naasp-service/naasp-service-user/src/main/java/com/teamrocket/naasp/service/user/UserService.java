package com.teamrocket.naasp.service.user;

import com.teamrocket.naasp.service.role.RoleService;
import com.teamrocket.naasp.service.role.exception.RoleNotFoundException;
import com.teamrocket.naasp.service.user.doa.UserDoa;
import com.teamrocket.naasp.service.user.model.User;
import com.teamrocket.naasp.service.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for updating, creating and getting user data.
 */
@Service
public class UserService {
    private RoleService roleService;
    private UserDoa userDoa;

    @Autowired
    public UserService (UserDoa userDoa, RoleService roleService) {
        this.userDoa = userDoa;
        this.roleService = roleService;
    }

    public User getUserByUserId (String userId) {
        if (!roleService.currentHasRole(UserRole.USER_READ)) {
            throw new RoleNotFoundException(UserRole.USER_READ);
        }

        return userDoa.get(userId);
    }

    public User updateUser (User user) {
        if (!roleService.currentHasRole(UserRole.USER_WRITE)) {
            throw new RoleNotFoundException(UserRole.USER_WRITE);
        }

        return userDoa.update(user);
    }

    public User createUser (User user) {
        if (!roleService.currentHasRole(UserRole.USER_WRITE)) {
            throw new RoleNotFoundException(UserRole.USER_WRITE);
        }

        return userDoa.create(user);
    }
}
