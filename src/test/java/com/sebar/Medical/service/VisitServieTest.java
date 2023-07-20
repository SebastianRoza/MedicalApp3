package com.sebar.Medical.service;

import com.sebar.Medical.mapper.PatientMapper;
import com.sebar.Medical.mapper.VisitMapper;
import com.sebar.Medical.model.dto.VisitCreationDto;
import com.sebar.Medical.model.dto.VisitDto;
import com.sebar.Medical.model.entity.Patient;
import com.sebar.Medical.model.entity.Visit;
import com.sebar.Medical.repository.VisitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class VisitServieTest {
    @Mock
    PatientMapper patientMapper;
    @Mock
    VisitMapper visitMapper;
    @Mock
    VisitRepository visitRepository;
    @InjectMocks
    PatientService patientService;
    @InjectMocks
    VisitService visitService;
    @Test
    void addVisit_sameHourNotBooked_visitAdded(){
        VisitCreationDto visitCreationDto=VisitCreationDto.builder()
                .visitTime(LocalDateTime.of(2034,12,12,12,00))
                .build();
        Visit visit= Visit.builder()
                .visitTime(LocalDateTime.of(2034,12,12,12,00))
                .build();
        VisitDto visitDto= VisitDto.builder()
                .visitTime(LocalDateTime.of(2034,12,12,12,00))
                .build();
        Mockito.when(visitRepository.findByVisitTime(eq(visitCreationDto.getVisitTime()))).thenReturn(Optional.empty());
        Mockito.when(visitMapper.toEntity(eq(visitCreationDto))).thenReturn(visit);
        Mockito.when(visitRepository.save(eq(visit))).thenReturn(visit);
        Mockito.when(visitMapper.toDto(eq(visit))).thenReturn(visitDto);

        var result= visitService.addVisit(visitCreationDto);

        Assertions.assertEquals(LocalDateTime.of(2034,12,12,12,00),result.getVisitTime());
    }
    @Test
    void showAllVisits_VisitsFoundAndConvertedToDto_ListOfVisistDto(){
        List<Visit> visitList=new ArrayList<>();
        Visit visit1=Visit.builder()
                .visitTime(LocalDateTime.of(2034,12,12,12,00))
                .build();
        Visit visit2=Visit.builder()
                .visitTime(LocalDateTime.of(2023,11,11,11,00))
                .build();
        visitList.add(visit1);
        visitList.add(visit2);
        VisitDto visitDto1=VisitDto.builder()
                .visitTime(LocalDateTime.of(2034,12,12,12,00))
                .build();
        VisitDto visitDto2=VisitDto.builder()
                .visitTime(LocalDateTime.of(2023,11,11,11,00))
                .build();
        Mockito.when(visitRepository.findAll()).thenReturn(visitList);
        Mockito.when(visitMapper.toDto(eq(visit1))).thenReturn(visitDto1);
        Mockito.when(visitMapper.toDto(eq(visit2))).thenReturn(visitDto2);

        var result=visitService.showAllVisits();

        Assertions.assertEquals(2,result.size());
        Assertions.assertEquals(LocalDateTime.of(2034,12,12,12,00),result.get(0).getVisitTime());
        Assertions.assertEquals(LocalDateTime.of(2023,11,11,11,00),result.get(1).getVisitTime());
    }
    @Test
    void assignPatientToVisit_VisitFoundAndNotAssigned_PatientAssignedToVisit(){
        Patient patient= Patient.builder()
                .id(1L)
                .email("zz")
                .build();
        Visit visit=Visit.builder()
                .id(1L)
                .visitTime(LocalDateTime.of(2034,12,12,12,00))
                .build();
        Mockito.when(visitRepository.findById(eq(visit.getId()))).thenReturn(Optional.of(visit));
        //visit.setPatient(patient);
        Mockito.when(visitRepository.save(visit)).thenReturn(visit);


        var result=visitService.assignPatientToVisit(1L,1L);

        Assertions.assertEquals("zz",result.getEmail());
        Assertions.assertEquals(1L,result.getId());

    }
    @Test
    void showPatientsVisits_VisitsExist_ListOfVisitDto(){
        List<Visit> visitList=new ArrayList<>();
        Visit visit1=Visit.builder()
                .visitTime(LocalDateTime.of(2034,12,12,12,00))
                .build();
        Visit visit2=Visit.builder()
                .visitTime(LocalDateTime.of(2023,11,11,11,00))
                .build();
        visitList.add(visit1);
        visitList.add(visit2);

    }
}
