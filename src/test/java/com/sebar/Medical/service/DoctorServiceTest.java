package com.sebar.Medical.service;

import com.sebar.Medical.exception.DoctorException;
import com.sebar.Medical.exception.FacilityException;
import com.sebar.Medical.exception.IllegalDoctorDataException;
import com.sebar.Medical.exception.VisitException;
import com.sebar.Medical.mapper.DoctorMapper;
import com.sebar.Medical.mapper.FacilityMapper;
import com.sebar.Medical.mapper.PatientMapper;
import com.sebar.Medical.mapper.VisitMapper;
import com.sebar.Medical.model.dto.*;
import com.sebar.Medical.model.entity.Doctor;
import com.sebar.Medical.model.entity.Facility;
import com.sebar.Medical.model.entity.Patient;
import com.sebar.Medical.model.entity.Visit;
import com.sebar.Medical.repository.DoctorRepository;
import com.sebar.Medical.repository.FacilityRepository;
import com.sebar.Medical.repository.VisitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    @Mock
    DoctorMapper doctorMapper;
    @Mock
    FacilityMapper facilityMapper;
    @Mock
    VisitMapper visitMapper;
    @Mock
    DoctorRepository doctorRepository;
    @Mock
    PatientMapper patientMapper;
    @Mock
    FacilityRepository facilityRepository;
    @InjectMocks
    DoctorService doctorService;
    @Mock
    VisitRepository visitRepository;

    @Test
    void addDoctor_AllDataCorrect_DoctorAdded() {
        DoctorCreationDto doctorCreationDto = DoctorCreationDto.builder()
                .email("xxx")
                .build();
        Doctor doctor = Doctor.builder()
                .email("xxx").build();
        DoctorDTO doctorDTO = DoctorDTO.builder().email("xxx").build();
        Mockito.when(doctorRepository.findByEmail(eq("xxx"))).thenReturn(Optional.empty());
        Mockito.when(doctorMapper.toEntity(eq(doctorCreationDto))).thenReturn(doctor);
        Mockito.when(doctorRepository.save(eq(doctor))).thenReturn(doctor);
        Mockito.when(doctorMapper.toDto(eq(doctor))).thenReturn(doctorDTO);

        var result = doctorService.addDoctor(doctorCreationDto);

        Assertions.assertEquals("xxx", result.getEmail());
    }

    @Test
    void addDoctor_DoctorExist_ExceptionThrown() {
        DoctorCreationDto doctorCreationDto = DoctorCreationDto.builder()
                .email("xxx")
                .build();
        Doctor doctor = Doctor.builder()
                .email("xxx").build();
        Mockito.when(doctorRepository.findByEmail(eq("xxx"))).thenReturn(Optional.of(doctor));

        var result = Assertions.assertThrows(DoctorException.class, () -> doctorService.addDoctor(doctorCreationDto));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
        Assertions.assertEquals("Doctor with this email already exist", result.getMessage());
    }

    @Test
    void addDoctor_EmailNull_ExceptionThrown() {
        DoctorCreationDto doctorCreationDto = DoctorCreationDto.builder().build();

        var result = Assertions.assertThrows(IllegalDoctorDataException.class, () -> doctorService.addDoctor(doctorCreationDto));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
        Assertions.assertEquals("Email can not be null", result.getMessage());
    }

    @Test
    void assignFacilityToDoctor_DataCorrect_FacilityAssignedToDoctor() {
        Doctor doctor = Doctor.builder().id(1L).build();
        Facility facility = Facility.builder().name("ss").id(1L).build();
        FacilityDto facilityDto = FacilityDto.builder().id(1L).build();
        List<Facility> facilityList=new ArrayList<>();
        doctor.setFacilities(facilityList);
        Mockito.when(facilityRepository.findById(eq(facility.getId()))).thenReturn(Optional.of(facility));
        Mockito.when(doctorRepository.findById(eq(doctor.getId()))).thenReturn(Optional.of(doctor));
        Mockito.when(doctorRepository.save(eq(doctor))).thenReturn(doctor);
        Mockito.when(facilityMapper.toDto(eq(facility))).thenReturn(facilityDto);

        var result = doctorService.assignFacilityToDoctor(1L, 1L);

        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    void assignFacilityToDoctor_FacilityNotFound_ExceptionThrown() {
        Facility facility = Facility.builder().name("ss").id(1L).build();
        Mockito.when(facilityRepository.findById(eq(facility.getId()))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(FacilityException.class, () -> doctorService.assignFacilityToDoctor(1L, 1L));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
        Assertions.assertEquals("Facility with this id does not exist", result.getMessage());
    }

    @Test
    void assignFacilityToDoctor_DoctorNotFound_ExceptionThrown() {
        Doctor doctor = Doctor.builder().id(1L).build();
        Facility facility = Facility.builder().name("ss").id(1L).build();
        Mockito.when(facilityRepository.findById(eq(facility.getId()))).thenReturn(Optional.of(facility));
        Mockito.when(doctorRepository.findById(eq(doctor.getId()))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(DoctorException.class, () -> doctorService.assignFacilityToDoctor(1L, 1L));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
        Assertions.assertEquals("Doctor with this id does not exist", result.getMessage());
    }

    @Test
    void assignFacilityToDoctor_DoctorIsAlreadyInFacility_ExceptionThrown() {
        Facility facility = Facility.builder().name("ss").id(1L).build();
        List<Facility> facilityList = new ArrayList<>();
        facilityList.add(facility);
        Doctor doctor = Doctor.builder().id(1L).facilities(facilityList).build();
        Mockito.when(facilityRepository.findById(eq(facility.getId()))).thenReturn(Optional.of(facility));
        Mockito.when(doctorRepository.findById(eq(doctor.getId()))).thenReturn(Optional.of(doctor));
        Mockito.when(doctorRepository.isDoctorAlreadyInFacility(doctor.getId(), facility.getId())).thenReturn(true);

        var result = Assertions.assertThrows(DoctorException.class, () -> doctorService.assignFacilityToDoctor(1L, 1L));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
        Assertions.assertEquals("Such a doctor is already in this facility", result.getMessage());
    }

    @Test
    void showAllFacilitiesWithGivenDoctor_DoctorFound_FacilitiesShown() {
        Doctor doctor = Doctor.builder().id(1L).build();
        Facility facility = Facility.builder().name("ss").id(1L).build();
        Facility facility2 = Facility.builder().name("xx").id(2L).build();
        FacilityDto facilityDto = FacilityDto.builder().name("ss").id(1L).build();
        FacilityDto facilityDto2 = FacilityDto.builder().name("xx").id(2L).build();
        List<Facility> facilityList = new ArrayList<>();
        facilityList.add(facility);
        facilityList.add(facility2);
        doctor.setFacilities(facilityList);
        Mockito.when(doctorRepository.findById(eq(doctor.getId()))).thenReturn(Optional.of(doctor));
        Mockito.when(facilityMapper.toDto(facility)).thenReturn(facilityDto);
        Mockito.when(facilityMapper.toDto(facility2)).thenReturn(facilityDto2);

        var result = doctorService.showAllFacilitiesWithGivenDoctor(1L);

        Assertions.assertEquals("ss", result.get(0).getName());
    }

    @Test
    void showAllFacilitiesWithGivenDoctor_DoctorNotFound_ExceptionThrown() {
        Doctor doctor = Doctor.builder().id(1L).build();
        Mockito.when(doctorRepository.findById(eq(doctor.getId()))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(DoctorException.class, () -> doctorService.showAllFacilitiesWithGivenDoctor(1L));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
        Assertions.assertEquals("Doctor not found", result.getMessage());
    }

    @Test
    void assignVisit_DataCorrect_visitAssigned() {
        Doctor doctor = Doctor.builder().id(1L).build();
        Visit visit = Visit.builder().id(1L).visitTime(LocalDateTime.of(2034, 12, 12, 12, 0)).build();
        Mockito.when(doctorRepository.findById(eq(doctor.getId()))).thenReturn(Optional.of(doctor));
        Mockito.when(visitRepository.findById(eq(visit.getId()))).thenReturn(Optional.of(visit));
        Mockito.when(visitRepository.save(visit)).thenReturn(visit);
        Mockito.when(visitMapper.toDto(visit)).thenReturn(VisitDto.builder().id(1L).build());

        var result = doctorService.assignVisit(1L, 1L);

        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    void assignVisit_DoctorAssigned_ExceptionThrown() {
        Doctor doctor = Doctor.builder().id(1L).build();
        Visit visit = Visit.builder().id(1L).visitTime(LocalDateTime.of(2034, 12, 12, 12, 0)).build();
        visit.setDoctor(doctor);
        Mockito.when(doctorRepository.findById(eq(doctor.getId()))).thenReturn(Optional.of(doctor));
        Mockito.when(visitRepository.findById(eq(visit.getId()))).thenReturn(Optional.of(visit));

        var result = Assertions.assertThrows(VisitException.class, () -> doctorService.assignVisit(1L, 1L));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    void getDoctorPatients_DoctorFound_PatientsShown() {
        Doctor doctor = Doctor.builder().id(1L).build();
        List<Patient> patientlist = new ArrayList<>();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        Patient patient = Patient.builder().build();
        PatientDTO patientDTO = PatientDTO.builder().id(1L).build();
        patientlist.add(patient);
        patientDTOS.add(patientDTO);
        Visit visit = Visit.builder().build();
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        Mockito.when(doctorRepository.findById(eq(doctor.getId()))).thenReturn(Optional.of(doctor));
        Mockito.when(visitRepository.findDoctorPatients(doctor.getId())).thenReturn(patientlist);
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);

        var result = doctorService.getDoctorPatients(1L);

        Assertions.assertEquals(1L, result.get(0).getId());
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void getDoctorPatients_DoctorNotFound_ExceptionThrown() {
        Doctor doctor = Doctor.builder().id(1L).build();
        Mockito.when(doctorRepository.findById(eq(doctor.getId()))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(DoctorException.class, () -> doctorService.getDoctorPatients(1L));

        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
        Assertions.assertEquals("Doctor not found", result.getMessage());
    }
}
