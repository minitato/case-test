package com.casetest.account.adapter.port.in.rest.handler;

import javax.security.auth.login.AccountException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.casetest.account.domain.exception.ExeccededLimitPerDayException;
import com.casetest.account.domain.exception.ThresholdExceededException;

@RestControllerAdvice
public class ErrorHandlerException {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> accountHandleException(AccountException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setDescription("Conta é inativo.");

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> limitPerDayHandleException(ExeccededLimitPerDayException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setDescription("Excedeu limite diário de 1000 reais.");

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> limitPerDayHandleException(ThresholdExceededException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setDescription("Limite de máximo de 10000 reais.");

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .body(errorResponse);
    }
}

class ErrorResponse {
    private int code;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

}
