package com.sebar.Medical.handler;

import com.sebar.Medical.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(PatientNotFoundException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(PatientException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(PatientException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(IllegalPatientDataException.class)
    public ResponseEntity<String> illegalPatientDateErrorResponse(IllegalPatientDataException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(MedicalException.class)
    public ResponseEntity<String> medicalErrorResponse(MedicalException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeErrorResponse(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error occurred");
    }

    @ExceptionHandler(IllegalVisitDateException.class)
    public ResponseEntity<String> illegalVisitDateErrorResponse(IllegalVisitDateException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(VisitException.class)
    public ResponseEntity<String> visitErrorResponse(VisitException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> nullPointerErrorResponse(VisitException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

}
