package com.teamrocket.naasp.service.commons.doa.exception;

public class GetObjectException extends RuntimeException {
    public GetObjectException(Class type, Object id, Throwable cause) {
        super("Failed to get object of type " + type.getCanonicalName()
                + " with id " + id + ".", cause);
    }
}
