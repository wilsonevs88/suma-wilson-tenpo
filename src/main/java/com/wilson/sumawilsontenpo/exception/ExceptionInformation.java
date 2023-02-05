package com.wilson.sumawilsontenpo.exception;


import com.wilson.sumawilsontenpo.utils.HttpStatus;

public interface ExceptionInformation {

    int getCode();
    String getDescription();
    HttpStatus getHttpStatus();

}
