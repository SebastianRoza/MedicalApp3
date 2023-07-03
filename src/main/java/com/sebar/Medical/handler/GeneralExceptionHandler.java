package com.sebar.Medical.handler;

import com.sebar.Medical.exception.MedicalException;
import com.sebar.Medical.exception.IllegalPatientDataException;
import com.sebar.Medical.exception.PatientException;
import com.sebar.Medical.exception.PatientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(PatientNotFoundException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
    @ExceptionHandler(PatientException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(PatientException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
    @ExceptionHandler(IllegalPatientDataException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(IllegalPatientDataException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
    @ExceptionHandler(MedicalException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(MedicalException e){
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(RuntimeException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error occurred");
    }


}
