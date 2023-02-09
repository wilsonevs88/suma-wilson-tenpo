package com.wilson.sumawilsontenpo.exception;

public class ConflictException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

}