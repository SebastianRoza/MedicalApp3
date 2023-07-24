package com.sebar.Medical.exception;

import org.springframework.http.HttpStatus;

public class IllegalFacilityDataException extends MedicalException{
    public IllegalFacilityDataException(String message ) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
