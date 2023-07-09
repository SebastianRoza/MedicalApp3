package com.sebar.Medical.service;

import com.sebar.Medical.exception.IllegalPatientDataException;
import com.sebar.Medical.exception.PatientException;
import com.sebar.Medical.exception.PatientNotFoundException;
import com.sebar.Medical.mapper.PatientMapper;
import com.sebar.Medical.model.dto.PatientCreationDTO;
import com.sebar.Medical.model.dto.PatientDTO;
import com.sebar.Medical.model.dto.PatientEditDTO;
import com.sebar.Medical.model.entity.Patient;
import com.sebar.Medical.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    PatientRepository patientRepository;
    @Mock
    PatientMapper patientMapper;
    @InjectMocks
    PatientService patientService;

    @Test
    void showPatient_PatientFound_PatientReturned() {
        Patient patient = new Patient();
        patient.setEmail("sr@gmail.com");
        Mockito.when(patientRepository.findByEmail(eq("sr@gmail.com"))).thenReturn(Optional.of(patient));
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("sr@gmail.com");
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);

        var result = patientService.showPatientByEmail("sr@gmail.com");

        Assertions.assertEquals("sr@gmail.com", result.getEmail());
    }

    @Test
    void showPatient_PatientNotfound_ExceptionThrown() {
        Patient patient = new Patient();
        patient.setEmail("sr@gmail.com");
        Mockito.when(patientRepository.findByEmail(eq("sxxx.com"))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.showPatientByEmail("sxxx.com"));

        Assertions.assertEquals("Patient does not exist in database", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void addPatient_SamePatientNotFound_PatientAddedToDb() {
        PatientCreationDTO patientCreationDTO = new PatientCreationDTO();
        patientCreationDTO.setEmail("sr@gmail.com");
        Patient patient = new Patient();
        patient.setEmail("sr@gmail.com");
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("sr@gmail.com");
        Mockito.when(patientRepository.findByEmail(eq(patientCreationDTO.getEmail()))).thenReturn(Optional.empty());
        Mockito.when(patientMapper.toEntity(eq(patientCreationDTO))).thenReturn(patient);
        Mockito.when(patientRepository.save(eq(patient))).thenReturn(patient);
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);

        var result= patientService.addPatient(patientCreationDTO);

        Assertions.assertEquals(patient.getEmail(), result.getEmail());
    }

    @Test
    void addPatient_PatientExist_PatientException() {
        PatientCreationDTO patientCreationDTO = new PatientCreationDTO();
        patientCreationDTO.setEmail("sr@gmail.com");
        Patient patient = new Patient();
        patient.setEmail("sr@gmail.com");
        Mockito.when(patientRepository.findByEmail(eq(patientCreationDTO.getEmail()))).thenReturn(Optional.of(patient));

        var result = Assertions.assertThrows(PatientException.class, () -> patientService.addPatient(patientCreationDTO));

        Assertions.assertEquals("Patient exist in database", result.getMessage());
        Assertions.assertEquals(HttpStatus.CONFLICT, result.getHttpStatus());
    }

    @Test
    void removePatient_PatientFound_PatientRemovedFromDb() {
        Patient patient = new Patient();
        Mockito.when(patientRepository.findByEmail(eq(patient.getEmail()))).thenReturn(Optional.of(patient));

        patientService.removePatientByEmail(patient.getEmail());

        Mockito.verify(patientRepository, Mockito.times(1)).delete(patient);
    }

    @Test
    void removePatient_PatientNotFound_ExceptionThrown() {
        Patient patient = new Patient();
        Mockito.when(patientRepository.findByEmail(eq(patient.getEmail()))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.removePatientByEmail(patient.getEmail()));

        Assertions.assertEquals("Patient does not exist in database", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void editPassword_PatientFound_PasswordChanged() {
        Patient patient = new Patient();
        patient.setEmail("xxx");
        patient.setPassword("zz");
        Mockito.when(patientRepository.findByEmail(eq("xxx"))).thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.save(eq(patient))).thenReturn(patient);

        patientService.editPassword("xxx", "yy");

        Assertions.assertEquals("yy", patient.getPassword());
    }

    @Test
    void editPassword_PatientNotFound_ThrownException() {
        Patient patient = new Patient();
        patient.setEmail("xxx");
        patient.setPassword("zz");
        Mockito.when(patientRepository.findByEmail(eq("yyy"))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.editPassword("yyy", "222"));

        Assertions.assertEquals("Patient does not exist in database", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void editPassword_PasswordIsNull_ThrownException() {
        Patient patient = new Patient();
        patient.setEmail("xxx");
        patient.setPassword(null);
        Mockito.when(patientRepository.findByEmail(eq("xxx"))).thenReturn(Optional.of(patient));

        var result = Assertions.assertThrows(IllegalPatientDataException.class, () -> patientService.editPassword("xxx", null));

        Assertions.assertEquals("Given password is null", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    void showAllPatients_PatientsFoundAndConvertedToDto_ListOfPatientsDto() {
        List<Patient> patients = new ArrayList<>();
        Patient patient = new Patient();
        patient.setEmail("zzz");
        Patient patient1 = new Patient();
        patient1.setEmail("yyy");
        patients.add(patient);
        patients.add(patient1);
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("zzz");
        PatientDTO patientDTO1 = new PatientDTO();
        patientDTO1.setEmail("yyy");
        Mockito.when(patientRepository.findAll()).thenReturn(patients);
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);
        Mockito.when(patientMapper.toDto(eq(patient1))).thenReturn(patientDTO1);

        var result = patientService.showAllPatients();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("zzz", result.get(0).getEmail());
        Assertions.assertEquals("yyy", result.get(1).getEmail());
    }

    @Test
    void editPatient_EmailChangeNotRequested_PatientEdited() {
        Patient patient = new Patient();
        patient.setEmail("xxx");
        PatientEditDTO patientEditDTO = new PatientEditDTO();
        patientEditDTO.setEmail("xxx");
        patientEditDTO.setPassword("d");
        patientEditDTO.setBirthday(LocalDate.EPOCH);
        patientEditDTO.setFirstName("a");
        patientEditDTO.setLastName("b");
        patientEditDTO.setPhoneNumber("c");
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("xxx");
        Mockito.when(patientRepository.findByEmail(eq("xxx"))).thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.save(eq(patient))).thenReturn(patient);
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);

        PatientDTO result = patientService.editPatient("xxx", patientEditDTO);

        Mockito.verify(patientRepository, Mockito.times(1)).save(eq(patient));
        Assertions.assertEquals("xxx", result.getEmail());
    }

    @Test
    void editPatient_DataAndEmailEditRequested_PatientEdited() {
        Patient patient = new Patient();
        patient.setEmail("xxx");
        PatientEditDTO patientEditDTO = new PatientEditDTO();
        patientEditDTO.setEmail("yyy");
        patientEditDTO.setPassword("d");
        patientEditDTO.setBirthday(LocalDate.EPOCH);
        patientEditDTO.setFirstName("a");
        patientEditDTO.setLastName("b");
        patientEditDTO.setPhoneNumber("c");
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("yyy");
        Mockito.when(patientRepository.findByEmail(eq("xxx"))).thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.findByEmail(eq("yyy"))).thenReturn(Optional.empty());
        Mockito.when(patientRepository.save(eq(patient))).thenReturn(patient);
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);

        PatientDTO result = patientService.editPatient("xxx", patientEditDTO);

        Mockito.verify(patientRepository, Mockito.times(1)).save(eq(patient));
        Assertions.assertEquals("yyy", result.getEmail());
    }

    @Test
    void editPatient_PatientNotFound_ExceptionThrown() {
        Patient patient = new Patient();
        patient.setEmail("xxx");
        PatientEditDTO patientEditDTO = new PatientEditDTO();
        Mockito.when(patientRepository.findByEmail(eq("yyy"))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(PatientNotFoundException.class, () -> patientService.editPatient("yyy", patientEditDTO));

        Assertions.assertEquals("Patient does not exist in database", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void editPatient_IsNotTheSamePatientAndPatientEditDtoIsPresent_PatientException() {
        Patient patient = new Patient();
        patient.setEmail("xxx");
        PatientEditDTO patientEditDTO = new PatientEditDTO();
        patientEditDTO.setEmail("yyy");
        Mockito.when(patientRepository.findByEmail(eq("xxx"))).thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.findByEmail(eq("yyy"))).thenReturn(Optional.of(patient));

        var result = Assertions.assertThrows(PatientException.class, () -> patientService.editPatient("xxx", patientEditDTO));

        Assertions.assertEquals("There exists a user with such an email", result.getMessage());
        Assertions.assertEquals(HttpStatus.CONFLICT, result.getHttpStatus());
    }

    @Test
    void editPatient_SomeInputValuesAreNull_IllegalPatientDataException() {
        Patient patient = new Patient();
        patient.setEmail("xxx");
        PatientEditDTO patientEditDTO = new PatientEditDTO();
        patientEditDTO.setEmail("yyy");
        patientEditDTO.setPassword("d");
        patientEditDTO.setBirthday(LocalDate.EPOCH);
        patientEditDTO.setFirstName(null);
        patientEditDTO.setLastName("b");
        patientEditDTO.setPhoneNumber("c");
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setEmail("yyy");
        Mockito.when(patientRepository.findByEmail(eq("xxx"))).thenReturn(Optional.of(patient));
        Mockito.when(patientRepository.findByEmail(eq("yyy"))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(IllegalPatientDataException.class, () -> patientService.editPatient("xxx", patientEditDTO));

        Assertions.assertEquals("Some input value is null", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

}
