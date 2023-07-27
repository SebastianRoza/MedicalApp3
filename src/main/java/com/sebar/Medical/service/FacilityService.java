package com.sebar.Medical.service;

import com.sebar.Medical.exception.FacilityException;
import com.sebar.Medical.exception.IllegalFacilityDataException;
import com.sebar.Medical.mapper.DoctorMapper;
import com.sebar.Medical.mapper.FacilityMapper;
import com.sebar.Medical.model.dto.DoctorDTO;
import com.sebar.Medical.model.dto.FacilityCreationDto;
import com.sebar.Medical.model.dto.FacilityDto;
import com.sebar.Medical.model.entity.Facility;
import com.sebar.Medical.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;
    private final DoctorMapper doctorMapper;

    public FacilityDto addFacility(FacilityCreationDto facilityCreationDto) {
        if (facilityCreationDto.getName() == null) {
            throw new IllegalFacilityDataException("Name can not be empty");
        }
        if (facilityRepository.findByName(facilityCreationDto.getName()).isPresent()) {
            throw new FacilityException("Facility with this name already exist");
        }
        Facility facility = facilityMapper.toEntity(facilityCreationDto);
        return facilityMapper.toDto(facilityRepository.save(facility));
    }

    public List<FacilityDto> showAllFacilities() {
        return facilityRepository.findAll()
                .stream()
                .map(facilityMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<DoctorDTO> showDoctorsAssignedToFacility(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new FacilityException("Facility not found"));
        return facility.getDoctors().stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());
    }
}
