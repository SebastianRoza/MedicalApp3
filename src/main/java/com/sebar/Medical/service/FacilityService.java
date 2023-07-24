package com.sebar.Medical.service;

import com.sebar.Medical.exception.FacilityException;
import com.sebar.Medical.exception.IllegalFacilityDataException;
import com.sebar.Medical.mapper.FacilityMapper;
import com.sebar.Medical.model.dto.FacilityCreationDto;
import com.sebar.Medical.model.dto.FacilityDto;
import com.sebar.Medical.model.entity.Facility;
import com.sebar.Medical.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;

    public FacilityDto addFacility(FacilityCreationDto facilityCreationDto) {
        if (facilityCreationDto.getName() == null) {
            throw new IllegalFacilityDataException("Name can not be null");
        }
        if (facilityRepository.findByName(facilityCreationDto.getName()).isPresent()) {
            throw new FacilityException("Facility with this name already exist");
        }
        Facility facility = facilityMapper.toEntity(facilityCreationDto);
        return facilityMapper.toDto(facilityRepository.save(facility));
    }
}
