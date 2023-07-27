package com.sebar.Medical.service;

import com.sebar.Medical.exception.VisitException;
import com.sebar.Medical.exception.IllegalVisitDateException;
import com.sebar.Medical.mapper.PatientMapper;
import com.sebar.Medical.mapper.VisitMapper;
import com.sebar.Medical.model.dto.PatientDTO;
import com.sebar.Medical.model.dto.VisitCreationDto;
import com.sebar.Medical.model.dto.VisitDto;
import com.sebar.Medical.model.entity.Patient;
import com.sebar.Medical.model.entity.Visit;
import com.sebar.Medical.repository.PatientRepository;
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
public class VisitServiceTest {
    @Mock
    PatientMapper patientMapper;
    @Mock
    VisitMapper visitMapper;
    @Mock
    VisitRepository visitRepository;
    @Mock
    PatientRepository patientRepository;
    @InjectMocks
    VisitService visitService;

    @Test
    void addVisit_sameHourNotBooked_visitAdded() {
        VisitCreationDto visitCreationDto = VisitCreationDto.builder()
                .visitTime(LocalDateTime.of(2034, 12, 12, 12, 0))
                .build();
        Visit visit = Visit.builder()
                .visitTime(LocalDateTime.of(2034, 12, 12, 12, 0))
                .build();
        VisitDto visitDto = VisitDto.builder()
                .visitTime(LocalDateTime.of(2034, 12, 12, 12, 0))
                .build();
        Mockito.when(visitRepository.findByVisitTime(eq(visitCreationDto.getVisitTime()))).thenReturn(Optional.empty());
        Mockito.when(visitMapper.toEntity(eq(visitCreationDto))).thenReturn(visit);
        Mockito.when(visitRepository.save(eq(visit))).thenReturn(visit);
        Mockito.when(visitMapper.toDto(eq(visit))).thenReturn(visitDto);

        var result = visitService.addVisit(visitCreationDto);

        Assertions.assertEquals(LocalDateTime.of(2034, 12, 12, 12, 0), result.
                getVisitTime());
    }

    @Test
    void addVisit_GivenHourBooked_ExceptionThrown() {
        VisitCreationDto visitCreationDto = new VisitCreationDto();
        visitCreationDto.setVisitTime(LocalDateTime.of(2034, 12, 12, 12, 0));
        Visit visit = new Visit();
        Mockito.when(visitRepository.findByVisitTime(eq(visitCreationDto.getVisitTime()))).thenReturn(Optional.of(visit));

        var result = Assertions.assertThrows(IllegalVisitDateException.class, () -> visitService.addVisit(visitCreationDto));

        Assertions.assertEquals("In this time slot, visit already exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    void addVisit_VisitsAreOverlapping_ExceptionThrown() {
        VisitCreationDto visitCreationDto = new VisitCreationDto();
        visitCreationDto.setVisitTime(LocalDateTime.of(2011, 12, 12, 12, 0));
        visitCreationDto.setEndVisitTime(LocalDateTime.of(2011, 12, 12, 12, 15));
        Visit visit = new Visit();
        List<Visit> visitList = new ArrayList<>();
        visitList.add(visit);
        Mockito.when(visitRepository.findByVisitTime(eq(visitCreationDto.getVisitTime()))).thenReturn(Optional.of(visit));
        Mockito.when(visitRepository.findAllOverlapping(eq(visitCreationDto.getVisitTime()), eq(visitCreationDto.getEndVisitTime()))).thenReturn(visitList);

        var result = Assertions.assertThrows(IllegalVisitDateException.class, () -> visitService.addVisit(visitCreationDto));

        Assertions.assertEquals("In this time slot, visits are overlapping", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    void addVisit_GivenHourIsInThePast_ExceptionThrown() {
        VisitCreationDto visitCreationDto = new VisitCreationDto();
        visitCreationDto.setVisitTime(LocalDateTime.of(2011, 12, 12, 12, 0));
        Mockito.when(visitRepository.findByVisitTime(eq(visitCreationDto.getVisitTime()))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(IllegalVisitDateException.class, () -> visitService.addVisit(visitCreationDto));

        Assertions.assertEquals("Input date is in the past or you did not insert a full hour", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }

    @Test
    void addVisit_GivenHourIsNotAQuarterOFHour_ExceptionThrown() {
        VisitCreationDto visitCreationDto = new VisitCreationDto();
        visitCreationDto.setVisitTime(LocalDateTime.of(2011, 12, 12, 12, 14));
        Mockito.when(visitRepository.findByVisitTime(eq(visitCreationDto.getVisitTime()))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(IllegalVisitDateException.class, () -> visitService.addVisit(visitCreationDto));

        Assertions.assertEquals("Input date is in the past or you did not insert a full hour", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }


    @Test
    void showAllVisits_VisitsFoundAndConvertedToDto_ListOfVisitsDto() {
        List<Visit> visitList = new ArrayList<>();
        Visit visit1 = Visit.builder()
                .visitTime(LocalDateTime.of(2034, 12, 12, 12, 0))
                .build();
        Visit visit2 = Visit.builder()
                .visitTime(LocalDateTime.of(2023, 11, 11, 11, 0))
                .build();
        visitList.add(visit1);
        visitList.add(visit2);
        VisitDto visitDto1 = VisitDto.builder()
                .visitTime(LocalDateTime.of(2034, 12, 12, 12, 0))
                .build();
        VisitDto visitDto2 = VisitDto.builder()
                .visitTime(LocalDateTime.of(2023, 11, 11, 11, 0))
                .build();
        Mockito.when(visitRepository.findAll()).thenReturn(visitList);
        Mockito.when(visitMapper.toDto(eq(visit1))).thenReturn(visitDto1);
        Mockito.when(visitMapper.toDto(eq(visit2))).thenReturn(visitDto2);

        var result = visitService.showAllVisits();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(LocalDateTime.of(2034, 12, 12, 12, 0), result.get(0).getVisitTime());
        Assertions.assertEquals(LocalDateTime.of(2023, 11, 11, 11, 0), result.get(1).getVisitTime());
    }

    @Test
    void assignPatientToVisit_VisitFoundAndNotAssigned_PatientAssignedToVisit() {
        Patient patient = Patient.builder()
                .id(5L)
                .email("zz")
                .build();
        PatientDTO patientDTO = PatientDTO.builder()
                .id(1L)
                .email("zz")
                .build();
        Visit visit = Visit.builder()
                .id(1L)
                .visitTime(LocalDateTime.of(2041, 12, 12, 12, 0))
                .build();
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.of(visit));
        Mockito.when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        Mockito.when(visitRepository.save(visit)).thenReturn(visit);
        Mockito.when(patientMapper.toDto(eq(patient))).thenReturn(patientDTO);

        var result = visitService.assignPatientToVisit(5L, 1L);

        Assertions.assertEquals("zz", result.getEmail());
        Assertions.assertEquals(1L, result.getId());
    }

    @Test
    void assignPatientToVisit_VisitNotFound_PatientNotAdded() {
        Mockito.when(visitRepository.findById(eq(1L))).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(VisitException.class , () -> visitService.assignPatientToVisit(1L, 1L));

        Assertions.assertEquals("No such a visit in system", result.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
    }


    @Test
    void showPatientsVisits_VisitsExist_ListOfVisitDto() {
        Patient patient = Patient.builder()
                .id(1L)
                .email("zz")
                .build();
        PatientDTO patientDTO = PatientDTO.builder()
                .email("zz")
                .build();
        List<Visit> visitList = new ArrayList<>();
        Visit visit1 = Visit.builder()
                .visitTime(LocalDateTime.of(2034, 12, 12, 12, 0))
                .patient(patient)
                .build();
        Visit visit2 = Visit.builder()
                .visitTime(LocalDateTime.of(2023, 11, 11, 11, 0))
                .patient(patient)
                .build();
        VisitDto visitDto1 = VisitDto.builder()
                .visitTime(LocalDateTime.of(2034, 12, 12, 12, 0))
                .patient(patientDTO)
                .build();
        VisitDto visitDto2 = VisitDto.builder()
                .visitTime(LocalDateTime.of(2023, 11, 11, 11, 0))
                .patient(patientDTO)
                .build();
        visitList.add(visit1);
        visitList.add(visit2);
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        Mockito.when(visitRepository.findByPatient(eq(patient))).thenReturn(visitList);
        Mockito.when(visitMapper.toDto(eq(visit1))).thenReturn(visitDto1);
        Mockito.when(visitMapper.toDto(eq(visit2))).thenReturn(visitDto2);

        var result = visitService.showPatientVisits(1L);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(LocalDateTime.of(2034, 12, 12, 12, 0), result.get(0).getVisitTime());
        Assertions.assertEquals(LocalDateTime.of(2023, 11, 11, 11, 0), result.get(1).getVisitTime());
    }

}
