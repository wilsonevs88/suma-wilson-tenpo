package com.wilson.sumawilsontenpo.utils;

import com.wilson.sumawilsontenpo.exception.ExceptionInformation;

import lombok.Getter;

@Getter
public enum ResponseCode implements ExceptionInformation {

    OK(0, "OK", HttpStatus.OK),
    ERROR_OBTAINING_USER_FROM_DATABASE(1010, "ERROR OBTAINING USER FROM DATABASE", HttpStatus.OK),
    ERROR_WHEN_OBTAINING_THE_USER_LIST_FROM_THE_DATABASE(1011, "ERROR WHEN OBTAINING THE USER LIST FROM THE DATABASE", HttpStatus.OK),
    ERROR_WHEN_SAVING_A_DATABASE_USER(1012, "ERROR WHEN SAVING A DATABASE USER", HttpStatus.OK),
    ERROR_UPDATING_A_DATABASE_USER(1013, "ERROR UPDATING A DATABASE USER", HttpStatus.OK),
    AUTH_ERROR(1090, "AUTH ERROR", HttpStatus.OK),
    CONNECTION_ERROR(1091, "CONNECTION ERROR", HttpStatus.OK),
    CONNECTION_PERCENTAGE_ERROR(1092, "PERCENT SERVICE CONNECTION ERROR", HttpStatus.OK),
    ;

    private int code;
    private String description;
    private HttpStatus httpStatus;

    ResponseCode(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    /**
     * Returns an {@link ExceptionInformation} with the given response code with an additional
     * description.
     */
    public static ExceptionInformation create(ResponseCode responseCode,
                                              String additionalDescription) {
        return new ExceptionInformation() {
            @Override
            public int getCode() {
                return responseCode.getCode();
            }

            @Override
            public String getDescription() {
                return responseCode.getDescription() + " " + additionalDescription;
            }

            @Override
            public HttpStatus getHttpStatus() {
                return responseCode.getHttpStatus();
            }
        };
    }
}
