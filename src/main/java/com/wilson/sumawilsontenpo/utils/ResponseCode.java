package com.wilson.sumawilsontenpo.utils;

import com.wilson.sumawilsontenpo.exception.ExceptionInformation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode implements ExceptionInformation {

    OK(0, "OK", HttpStatus.OK),
    ENTER_THE_SIZE_OF_PAGES_TO_DISPLAY(1010, "ENTER THE SIZE OF PAGES TO DISPLAY", HttpStatus.OK),
    ENTER_THE_SIZE_OF_RECORDS_TO_DISPLAY(1011,"ENTER THE SIZE OF RECORDS TO DISPLAY", HttpStatus.OK),
    ERROR_OBTAINING_USER_FROM_DATABASE(1020, "ERROR OBTAINING USER FROM DATABASE", HttpStatus.OK),
    ERROR_WHEN_OBTAINING_THE_USER_LIST_FROM_THE_DATABASE(1021, "ERROR WHEN OBTAINING THE USER LIST FROM THE DATABASE", HttpStatus.OK),
    ERROR_WHEN_SAVING_A_DATABASE_USER(1022, "ERROR WHEN SAVING A DATABASE USER", HttpStatus.OK),
    ERROR_UPDATING_A_DATABASE_USER(1023, "ERROR UPDATING A DATABASE USER", HttpStatus.OK),
    CUSTOMER_NOT_FOUND_OR_NOT_ACTIVE(1030, "CUSTOMER NOT FOUND OR NOT ACTIVE", HttpStatus.OK),
    WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS(1031, "WE DO NOT HAVE THAT NUMBER OF RECORDS", HttpStatus.OK),
    ENTERED_NUMBER_MUST_BE_GREATER_THAN_0(1032, "ENTERED NUMBER MUST BE GREATER THAN 0", HttpStatus.OK),
    AUTH_ERROR(1090, "AUTH ERROR", HttpStatus.OK),
    CONNECTION_ERROR(1091, "CONNECTION ERROR", HttpStatus.OK),
    CONNECTION_PERCENTAGE_ERROR(1092, "PERCENT SERVICE CONNECTION ERROR", HttpStatus.OK),
    ;

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    ResponseCode(int code, String description) {
        this(code, description, HttpStatus.OK);
    }

    /**
     * Returns an {@link ExceptionInformation} with the given response code with an additional
     * description.
     */
    public static ExceptionInformation create(ResponseCode responseCode, String additionalDescription) {
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
