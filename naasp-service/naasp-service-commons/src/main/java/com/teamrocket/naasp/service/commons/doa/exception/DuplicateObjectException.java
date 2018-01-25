package com.teamrocket.naasp.service.commons.doa.exception;

public class DuplicateObjectException extends RuntimeException {
    public DuplicateObjectException(Class type, Object object, Throwable cause) {
        super("Duplicate object of type " + type.getCanonicalName()
                + ". Object: " + object.toString(), cause);
    }
}
