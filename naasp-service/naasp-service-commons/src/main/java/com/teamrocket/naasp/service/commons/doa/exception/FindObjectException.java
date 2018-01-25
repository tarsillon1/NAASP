package com.teamrocket.naasp.service.commons.doa.exception;

public class FindObjectException extends RuntimeException {
    public FindObjectException(Class type, Object search, Throwable cause) {
        super("Failed to find object(s) of type " + type.getCanonicalName()
                + " with search parameters " + search + ".", cause);
    }
}
