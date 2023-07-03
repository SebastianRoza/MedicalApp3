package com.sebar.Medical.exception;

import org.springframework.http.HttpStatus;

public class PatientNotFoundException extends MedicalException {
    public PatientNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
