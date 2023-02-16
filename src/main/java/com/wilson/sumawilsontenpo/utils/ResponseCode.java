package com.wilson.sumawilsontenpo.utils;

import com.wilson.sumawilsontenpo.exception.ExceptionInformation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode implements ExceptionInformation {

    OK(0, "OK", HttpStatus.OK),
    NECESSARY_TO_ENTER_VALUE_ONE(1010, "NECESSARY TO ENTER VALUE ONE", HttpStatus.OK),
    NECESSARY_TO_ENTER_VALUE_TWO(1011, "NECESSARY TO ENTER VALUE TWO", HttpStatus.OK),
    MISSING_ENTER_CLIENT_ID(1012, "MISSING ENTER CLIENT ID", HttpStatus.OK),
    MISSING_ENTER_USER_ID(1013, "MISSING ENTER USER ID", HttpStatus.OK),
    ERROR_OBTAINING_USER_FROM_DATABASE(1020, "ERROR OBTAINING USER FROM DATABASE", HttpStatus.OK),
    ERROR_WHEN_OBTAINING_THE_USER_LIST_FROM_THE_DATABASE(1021, "ERROR WHEN OBTAINING THE USER LIST FROM THE DATABASE", HttpStatus.OK),
    ERROR_WHEN_SAVING_A_DATABASE_USER(1022, "ERROR WHEN SAVING A DATABASE USER", HttpStatus.OK),
    CUSTOMER_NOT_FOUND_OR_NOT_ACTIVE(1030, "CUSTOMER NOT FOUND OR NOT ACTIVE", HttpStatus.OK),
    WE_DO_NOT_HAVE_THAT_NUMBER_OF_RECORDS(1040, "WE DO NOT HAVE THAT NUMBER OF RECORDS", HttpStatus.OK),
    ENTERED_NUMBER_MUST_BE_GREATER_THAN_0(1050, "ENTERED NUMBER MUST BE GREATER THAN 0", HttpStatus.OK),
    FAILURE_GETTING_COUNTER_REDIS(1060, "Failure getting counter to the redis queue", HttpStatus.OK),
    FAILURE_CHECKING_SESSION_REDIS(1061, "Failure checking session to the redis queue", HttpStatus.OK),
    FAILURE_ADDING_COUNTER_REDIS(1062, "Failure adding counter to the redis queue", HttpStatus.OK),
    FAILURE_UPDATING_COUNTER_REDIS(1063, "Failure updating counter to the redis queue", HttpStatus.OK),
    FAILURE_DELETING_COUNTER_REDIS(1064, "Failure deleting counter within redis queue", HttpStatus.OK),
    CONNECTION_PERCENTAGE_ERROR(9000, "PERCENT SERVICE CONNECTION ERROR", HttpStatus.OK),
    MAXIMUM_QUERIES_PER_MINUTE(901, "MAXIMUM QUERIES PER MINUTE", HttpStatus.OK),
    USER_BLOCKED(9002, "USER BLOCKED, TRY AGAIN IN 1 MINUTE", HttpStatus.OK),
    ;

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;


    /**
     * Allows to get any exception.
     *
     * @param responseCode Returns the result response.
     */
    public static ExceptionInformation create(ResponseCode responseCode) {
        return new ExceptionInformation() {
            @Override
            public int getCode() {
                return responseCode.getCode();
            }

            @Override
            public String getDescription() {
                return responseCode.getDescription();
            }

            @Override
            public HttpStatus getHttpStatus() {
                return responseCode.getHttpStatus();
            }

        };
    }

}
