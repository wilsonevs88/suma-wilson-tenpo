package com.wilson.sumawilsontenpo.exception;

public class DatosInvalidosExcepcion extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DatosInvalidosExcepcion(String message, Throwable cause) {
        super(message, cause);
    }

}