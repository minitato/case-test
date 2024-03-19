package com.casetest.registration.adapters.in.rest.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.casetest.registration.domain.exception.CustomerNotFoundException;

@RestControllerAdvice
public class ErrorHandlerException {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CustomerNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setDescription("Cliente não encontrado.");

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
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
