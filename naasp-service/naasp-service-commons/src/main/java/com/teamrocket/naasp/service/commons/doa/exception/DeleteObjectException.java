package com.teamrocket.naasp.service.commons.doa.exception;

public class DeleteObjectException extends RuntimeException {
    public DeleteObjectException(Class type, Object id, Throwable cause) {
        super("Failed to delete object of type " + type.getCanonicalName()
                + " with id " + id + ".", cause);
    }
}
