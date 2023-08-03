package com.sebar.Medical.service;

import com.sebar.Medical.model.dto.PatientCreationDTO;
import com.sebar.Medical.model.dto.PatientDTO;
import com.sebar.Medical.model.dto.PatientEditDTO;
import com.sebar.Medical.exception.IllegalPatientDataException;
import com.sebar.Medical.exception.PatientException;
import com.sebar.Medical.exception.PatientNotFoundException;
import com.sebar.Medical.mapper.PatientMapper;
import com.sebar.Medical.repository.PatientRepository;
import com.sebar.Medical.model.entity.Patient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    public List<PatientDTO> showAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

    public PatientDTO addPatient(PatientCreationDTO patientCreationDTO) {
        log.info("Add patient: {} to database",patientCreationDTO);
        Optional<Patient> patient = patientRepository.findByEmail(patientCreationDTO.getEmail());
        if (patient.isPresent()) {
            throw new PatientException("Patient with this email:"+patientCreationDTO.getEmail()+" exist in database", HttpStatus.CONFLICT);
        }
        Patient patient2 = patientMapper.toEntity(patientCreationDTO);
        return patientMapper.toDto(patientRepository.save(patient2));
    }

    public PatientDTO showPatientByEmail(String email) {
        log.info("Show patient with given email: {}",email);
        return patientRepository.findByEmail(email)
                .map(patientMapper::toDto)
                .orElseThrow(() -> new PatientNotFoundException("Patient does not exist in database"));
    }

    public void removePatientByEmail(String email) {
        log.info("Remove patient with given email: {}",email);
        Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new PatientNotFoundException("Patient with this email:"+email+" not exist in database"));
        patientRepository.delete(patient);
    }

    public PatientDTO editPatient(String email, PatientEditDTO editInfoDTO) {
        log.info("Edit information of patient with given email: {}, to given datas: {}",email,editInfoDTO);
        Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new PatientNotFoundException("Patient with this email: "+email+"does not exist in database"));
        Optional<Patient> patient1 = patientRepository.findByEmail(editInfoDTO.getEmail());
        boolean isNotTheSamePatient = !patient.getEmail().equals(editInfoDTO.getEmail());
        if (isNotTheSamePatient && patient1.isPresent()) {
            throw new PatientException("There exists a user with such an email"+email, HttpStatus.CONFLICT);   //BAD_REQUEST ewentualnie
        }
        if (editInfoDTO.getPassword() == null || editInfoDTO.getFirstName() == null || editInfoDTO.getLastName() == null
                || editInfoDTO.getBirthday() == null || editInfoDTO.getEmail() == null || editInfoDTO.getPhoneNumber() == null) {
            throw new IllegalPatientDataException("Some input value is null from:"+editInfoDTO);
        }
        patient.update(editInfoDTO);
        return patientMapper.toDto(patientRepository.save(patient));
    }

    public void editPassword(String email, String password) {
        log.info("Change password of user with email: {}, to {}",email,password);
        Patient patient = patientRepository.findByEmail(email).orElseThrow(() -> new PatientNotFoundException("Patient with this email"+email+"does not exist in database"));
        if (password == null) {
            throw new IllegalPatientDataException("Given password"+password+" is null");
        }
        patient.setPassword(password);
        patientRepository.save(patient);
    }

}
