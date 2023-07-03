package com.sebar.Medical.exception;

import com.sebar.Medical.model.Patient;
import org.springframework.http.HttpStatus;

public class PatientException extends MedicalException{
    public PatientException(String message, HttpStatus httpStatus){
        super(message,httpStatus);
    }

}
