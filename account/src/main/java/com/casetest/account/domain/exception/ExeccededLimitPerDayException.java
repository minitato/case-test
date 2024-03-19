package com.casetest.account.domain.exception;

public class ExeccededLimitPerDayException extends RuntimeException {

    public ExeccededLimitPerDayException(String message) {
        super(message);
    }

}
