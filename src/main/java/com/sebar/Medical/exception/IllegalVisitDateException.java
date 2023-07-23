package com.sebar.Medical.exception;

import org.springframework.http.HttpStatus;

public class IllegalVisitDateException extends MedicalException {
    public IllegalVisitDateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
