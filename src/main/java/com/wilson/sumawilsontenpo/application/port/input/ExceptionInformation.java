package com.wilson.sumawilsontenpo.application.port.input;

import org.springframework.http.HttpStatus;

public interface ExceptionInformation {

    int getCode();

    String getDescription();

    HttpStatus getHttpStatus();

}
