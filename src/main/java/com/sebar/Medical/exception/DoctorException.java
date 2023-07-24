package com.sebar.Medical.exception;

import org.springframework.http.HttpStatus;

public class DoctorException extends MedicalException {
    public DoctorException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
