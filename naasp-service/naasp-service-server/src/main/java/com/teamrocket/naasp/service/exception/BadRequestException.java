package com.teamrocket.naasp.service.exception;

public class BadRequestException extends NAASPException {
    public BadRequestException(String message) {
        super(NAASPException.BAD_REQUEST, NAASPException.BAD_REQUEST, message);
    }
}