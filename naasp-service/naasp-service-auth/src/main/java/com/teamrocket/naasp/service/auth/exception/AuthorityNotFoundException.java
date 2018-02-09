package com.teamrocket.naasp.service.auth.exception;

public class AuthorityNotFoundException extends RuntimeException {
    public AuthorityNotFoundException (String authority) {
        super("Authority not found in security context: " + authority + ".");
    }
}
