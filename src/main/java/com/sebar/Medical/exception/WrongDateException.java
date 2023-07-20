package com.sebar.Medical.exception;

import org.springframework.http.HttpStatus;

public class WrongDateException extends MedicalException{
    public WrongDateException(String message){
        super(message, HttpStatus.BAD_REQUEST);
    }
}
