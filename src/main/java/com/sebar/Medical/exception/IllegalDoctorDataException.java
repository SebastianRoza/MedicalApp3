package com.sebar.Medical.exception;

import org.springframework.http.HttpStatus;

public class IllegalDoctorDataException extends MedicalException{
    public IllegalDoctorDataException(String message ) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
