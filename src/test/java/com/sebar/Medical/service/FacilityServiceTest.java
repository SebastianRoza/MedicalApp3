package com.sebar.Medical.service;

import com.sebar.Medical.exception.FacilityException;
import com.sebar.Medical.exception.IllegalFacilityDataException;
import com.sebar.Medical.mapper.DoctorMapper;
import com.sebar.Medical.mapper.FacilityMapper;
import com.sebar.Medical.model.dto.DoctorDTO;
import com.sebar.Medical.model.dto.FacilityCreationDto;
import com.sebar.Medical.model.dto.FacilityDto;
import com.sebar.Medical.model.entity.Doctor;
import com.sebar.Medical.model.entity.Facility;
import com.sebar.Medical.repository.FacilityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;


@ExtendWith(MockitoExtension.class)
public class FacilityServiceTest {
    @Mock
    FacilityMapper facilityMapper;
    @Mock
    DoctorMapper doctorMapper;
    @Mock
    FacilityRepository facilityRepository;
    @InjectMocks
    FacilityService facilityService;

    @Test
    void addFacility_FacilityNotExist_FacilityAdded() {
        FacilityCreationDto facilityCreationDto = FacilityCreationDto.builder().name("ss").build();
        Facility facility = Facility.builder().name("ss").build();
        FacilityDto facilityDto = FacilityDto.builder().name("ss").build();
        Mockito.when(facilityRepository.findByName("ss")).thenReturn(Optional.empty());
        Mockito.when(facilityMapper.toEntity(facilityCreationDto)).thenReturn(facility);
        Mockito.when(facilityRepository.save(facility)).thenReturn(facility);
        Mockito.when(facilityMapper.toDto(facility)).thenReturn(facilityDto);

        var result = facilityService.addFacility(facilityCreationDto);

        Assertions.assertEquals("ss", result.getName());
    }

    @Test
    void addFacility_FacilityExist_FacilityNotAdded() {
        FacilityCreationDto facilityCreationDto = FacilityCreationDto.builder().name("ss").build();
        Facility facility = Facility.builder().name("ss").build();
        Mockito.when(facilityRepository.findByName("ss")).thenReturn(Optional.of(facility));

        var result = Assertions.assertThrows(FacilityException.class, () -> facilityService.addFacility(facilityCreationDto));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
        Assertions.assertEquals("Facility with this name already exist", result.getMessage());
    }

    @Test
    void addFacility_NameNull_FacilityNotAdded() {
        FacilityCreationDto facilityCreationDto = FacilityCreationDto.builder().build();

        var result = Assertions.assertThrows(IllegalFacilityDataException.class, () -> facilityService.addFacility(facilityCreationDto));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
        Assertions.assertEquals("Name can not be empty", result.getMessage());
    }

    @Test
    void showDoctorsAssignedToFacility_FacilityFound_DoctorsShown() {
        Facility facility = Facility.builder().name("ss").id(1L).build();
        Doctor doctor = Doctor.builder().email("xx").build();
        DoctorDTO doctorDTO = DoctorDTO.builder().email("xx").build();
        Doctor doctor2 = Doctor.builder().email("yy").build();
        DoctorDTO doctorDTO2 = DoctorDTO.builder().email("yy").build();
        List<Doctor> doctorList = new ArrayList<>();
        doctorList.add(doctor);
        doctorList.add(doctor2);
        facility.setDoctors(doctorList);
        Mockito.when(facilityRepository.findById(eq(facility.getId()))).thenReturn(Optional.of(facility));
        Mockito.when(doctorMapper.toDto(doctor)).thenReturn(doctorDTO);
        Mockito.when(doctorMapper.toDto(doctor2)).thenReturn(doctorDTO2);

        var result = facilityService.showDoctorsAssignedToFacility(1L);

        Assertions.assertEquals("xx", result.get(0).getEmail());
    }

    @Test
    void showDoctorsAssignedToFacility_FacilityNotFound_DoctorsNotShown() {
        Facility facility = Facility.builder().name("ss").id(1L).build();
        Mockito.when(facilityRepository.findById(facility.getId())).thenReturn(Optional.empty());

        var result = Assertions.assertThrows(FacilityException.class, () -> facilityService.showDoctorsAssignedToFacility(1L));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getHttpStatus());
        Assertions.assertEquals("Facility not found", result.getMessage());
    }

}
