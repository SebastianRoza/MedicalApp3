package com.sebar.Medical.service;

import com.sebar.Medical.exception.DoctorException;
import com.sebar.Medical.exception.IllegalDoctorDataException;
import com.sebar.Medical.mapper.DoctorMapper;
import com.sebar.Medical.model.dto.DoctorCreationDto;
import com.sebar.Medical.model.dto.DoctorDTO;
import com.sebar.Medical.model.entity.Doctor;
import com.sebar.Medical.model.entity.Facility;
import com.sebar.Medical.repository.DoctorRepository;
import com.sebar.Medical.repository.FacilityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    @Mock
    DoctorMapper doctorMapper;
    @Mock
    DoctorRepository doctorRepository;
    @Mock
    FacilityRepository facilityRepository;
    @InjectMocks
    DoctorService doctorService;

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

        Assertions.assertEquals(HttpStatus.CONFLICT, result.getHttpStatus());
        Assertions.assertEquals("Doctor with this email already exist", result.getMessage());
    }

    @Test
    void addDoctor_EmailNull_ExceptionThrown() {
        DoctorCreationDto doctorCreationDto = DoctorCreationDto.builder()
                .build();

        var result = Assertions.assertThrows(IllegalDoctorDataException.class, () -> doctorService.addDoctor(doctorCreationDto));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
        Assertions.assertEquals("Email can not be null", result.getMessage());
    }

    @Test
    void assignFacilityToDoctor_DataCorrect_FacilityAssignedToDoctor() {
        Doctor doctor = new Doctor();
        Facility facility = new Facility();
        Mockito.when(facilityRepository.findById(eq(1L))).thenReturn(Optional.of(facility));
        Mockito.when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        Mockito.when(doctorRepository.isDoctorAlreadyInFacility(1L, 1L)).thenReturn(false);
        Mockito.when(doctorRepository.save(eq(doctor))).thenReturn(doctor);

        var result = doctorService.assignFacilityToDoctor(1L, 1L);

        Assertions.assertEquals("Facility assigned", result);
    }
}
