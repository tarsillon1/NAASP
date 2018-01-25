package com.teamrocket.naasp.service.commons.doa.exception;

public class UpdateObjectException extends RuntimeException {
    public UpdateObjectException(Class type, Object id, Throwable cause) {
        super("Failed to update object of type " + type.getCanonicalName()
                + " with id " + id + ".", cause);
    }
}
