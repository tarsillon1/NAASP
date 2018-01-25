package com.teamrocket.naasp.service.exception;

/**
 * Created by bruce.skingle on 1/21/16.
 */
public class NAASPException extends Exception {

    // Request errors
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int BAD_REQUEST = 400;
    public static final int NOT_IMPLEMENTED = 404;
    public static final int NOT_FOUND = 404;
    public static final int UNPROCESSABLE_ENTITY = 422;

    // Internal Errors
    public static final int INTERNAL_ERROR = 500;


    private int             httpStatusCode_;
    private int             errorCode_;

    public NAASPException(int httpStatusCode, int errorCode, String message) {
        super(message);
        httpStatusCode_ = httpStatusCode;
        errorCode_ = errorCode;
    }

    public NAASPException(int httpStatusCode, int errorCode, String message, Throwable cause) {
        super(message, cause);
        httpStatusCode_ = httpStatusCode;
        errorCode_ = errorCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode_;
    }

    public int getErrorCode() {
        return errorCode_;
    }

    public void setStatus(int httpStatusCode) {
        httpStatusCode_ = httpStatusCode;
    }

    public void setErrorCode(int errorCode) {
        errorCode_ = errorCode;
    }
}