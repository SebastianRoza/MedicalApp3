package com.sebar.Medical.exception;

import org.springframework.http.HttpStatus;

public class VisitException extends MedicalException {
    public VisitException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
