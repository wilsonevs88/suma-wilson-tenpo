package com.wilson.sumawilsontenpo.exception;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * <p>Base exception that will be handled by the exception handler controller. Given a {@link
 * ExceptionInformation}, the ExceptionHandlerController will return a response with the given code,
 * description and {@link HttpStatus}.</p>
 * <br>
 * <p>If exists a reporting service (for example:Sentry), registered in the spring application
 * context, if explicity pointed out, the exception will be reported to it by the
 * ExceptionHandlerController.</p>
 */


public class ExceptionReturn extends RuntimeException {

    public static final long serialVersionUID = 1L;

    private transient ExceptionInformation exceptionInformation;
    private final boolean reportException;

    /**
     * Constructs {@link ExceptionReturn}.
     *
     * @param exceptionInformation {@link ExceptionInformation} contains information that will be
     *                             return by the exception controller handler
     * @param causeMessage         message that will be returned by the {@link Exception}
     * @param reportException      indicates if the exception should be reported
     */
    public ExceptionReturn(ExceptionInformation exceptionInformation, String causeMessage,
                           boolean reportException) {
        super(causeMessage);
        this.exceptionInformation = exceptionInformation;
        this.reportException = reportException;
    }

    public ExceptionReturn(ExceptionInformation exceptionInformation, boolean reportException) {
        this(exceptionInformation, exceptionInformation.getDescription(), reportException);
    }

    public ExceptionReturn(ExceptionInformation exceptionInformation, String causeMessage) {
        this(exceptionInformation, causeMessage, false);
    }

    public ExceptionReturn(ExceptionInformation exceptionInformation) {
        this(exceptionInformation, exceptionInformation.getDescription(), false);
    }

    /**
     * Constructs {@link ExceptionReturn}.
     *
     * @param exceptionInformation {@link ExceptionInformation} contains information that will be *
     *                             return by the exception controller handler
     * @param cause                cause
     * @param causeMessage         message that will be returned by the {@link Exception}
     * @param reportException      indicates if the exception should be reported
     */
    public ExceptionReturn(ExceptionInformation exceptionInformation, String causeMessage,
                           Throwable cause, boolean reportException) {
        super(causeMessage, cause, true, false);
        this.exceptionInformation = exceptionInformation;
        this.reportException = reportException;
    }

    public ExceptionReturn(ExceptionInformation exceptionInformation, Throwable cause,
                           boolean reportException) {
        this(exceptionInformation, exceptionInformation.getDescription(), cause, reportException);
    }

    public ExceptionReturn(ExceptionInformation exceptionInformation,
                           String exceptionMessage, Throwable cause) {
        this(exceptionInformation, exceptionMessage, cause, false);
    }

    public ExceptionReturn(ExceptionInformation exceptionInformation, Throwable cause) {
        this(exceptionInformation, exceptionInformation.getDescription(), cause, false);
    }


    public final ExceptionInformation getExceptionInformation() {
        return exceptionInformation;
    }

    public final boolean isReportException() {
        return reportException;
    }


    public static final class Builder {

        private ExceptionInformation exceptionInformation;
        private boolean reportException;
        private Throwable cause;
        private String causeMessage;

        private Builder() {
        }

        /**
         * Creates a {@link ExceptionInformation} builder.
         *
         * @param exceptionInformation {@link ExceptionInformation} contains information that will be *
         *                             return by the exception controller handler
         * @return {@link ExceptionInformation} builder
         */
        public static Builder create(ExceptionInformation exceptionInformation) {
            final Builder builder = new Builder();
            builder.exceptionInformation = exceptionInformation;
            builder.reportException = false;

            return builder;
        }

        public Builder reportException(boolean reportException) {
            this.reportException = reportException;
            return this;
        }

        public Builder cause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public Builder causeMessage(String causeMessage) {
            this.causeMessage = causeMessage;
            return this;
        }

        /**
         * Builds a {@link ExceptionInformation}.
         *
         * @return {@link ExceptionInformation}
         */
        public ExceptionReturn build() {
            if (nonNull(cause) && nonNull(causeMessage)) {
                return new ExceptionReturn(exceptionInformation, causeMessage, cause, reportException);
            } else if (isNull(cause) && nonNull(causeMessage)) {
                return new ExceptionReturn(exceptionInformation, causeMessage, reportException);
            } else if (nonNull(cause)) {
                return new ExceptionReturn(exceptionInformation, cause, reportException);
            }

            return new ExceptionReturn(exceptionInformation, reportException);
        }
    }

}
