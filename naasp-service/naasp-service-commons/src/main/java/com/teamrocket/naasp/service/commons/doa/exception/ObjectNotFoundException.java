package com.teamrocket.naasp.service.commons.doa.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(Class type, Object id) {
        super("Failed to find object of type " + type.getCanonicalName()
                + " with id " + id + ".");
    }
}
