package com.sebar.Medical.exception;

import org.springframework.http.HttpStatus;

public class IllegalPatientDataException extends MedicalException{
    public IllegalPatientDataException(String message ){
        super(message, HttpStatus.BAD_REQUEST);
    }
}
