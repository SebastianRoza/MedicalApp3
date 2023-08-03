package com.sebar.Medical.handler;

import com.sebar.Medical.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(PatientNotFoundException e) {
        log.error(e.toString());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(PatientException.class)
    public ResponseEntity<String> patientExceptionErrorResponse(PatientException e) {
        log.error(e.toString());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(IllegalPatientDataException.class)
    public ResponseEntity<String> illegalPatientDateErrorResponse(IllegalPatientDataException e) {
        log.error(e.toString());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(MedicalException.class)
    public ResponseEntity<String> medicalErrorResponse(MedicalException e) {
        log.error(e.toString());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeErrorResponse(RuntimeException e) {
        log.error(e.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown error occurred");
    }

    @ExceptionHandler(IllegalVisitDateException.class)
    public ResponseEntity<String> illegalVisitDateErrorResponse(IllegalVisitDateException e) {
        log.error(e.toString());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(VisitException.class)
    public ResponseEntity<String> visitErrorResponse(VisitException e) {
        log.error(e.toString());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> nullPointerErrorResponse(VisitException e) {
        log.error(e.toString());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
    @ExceptionHandler(DoctorException.class)
    public ResponseEntity<String> DoctorExceptionErrorResponse(DoctorException e) {
        log.error(e.toString());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
    @ExceptionHandler(FacilityException.class)
    public ResponseEntity<String> FacilityExceptionErrorResponse(FacilityException e) {
        log.error(e.toString());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

}
