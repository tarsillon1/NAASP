package com.teamrocket.naasp.service.commons.doa.exception;

public class CreateObjectException extends RuntimeException {
    public CreateObjectException(Class type, Object object, Throwable cause) {
        super("Failed to create new object of type " + type.getCanonicalName()
                + ". Object: " + object, cause);
    }
}
