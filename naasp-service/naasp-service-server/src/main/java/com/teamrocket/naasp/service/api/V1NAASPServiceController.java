package com.teamrocket.naasp.service.api;

import com.teamrocket.naasp.service.auth.AuthService;
import com.teamrocket.naasp.service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class V1NAASPServiceController extends V1ApiController {
    @Autowired
    private AuthService authService;

    @Override
    public User adminUserCreate(UserCreate userCreate) {
        return null;
    }

    @Override
    public SuccessResponse adminUserDelete(String uid) {
        return null;
    }

    @Override
    public UserList adminUserSearch(UserSearch userSearch) {
        return null;
    }

    @Override
    public User adminUserUpdate(String uid, User user) {
        return null;
    }

    @Override
    public SuccessResponse healthcheck() {
        return new SuccessResponse().message("This test worked!");
    }
}
