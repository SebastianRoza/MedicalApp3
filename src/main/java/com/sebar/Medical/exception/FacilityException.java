package com.sebar.Medical.exception;

import org.springframework.http.HttpStatus;

public class FacilityException extends MedicalException {
    public FacilityException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
