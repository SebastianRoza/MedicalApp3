package com.sebar.Medical.mapper;

import com.sebar.Medical.model.dto.FacilityCreationDto;
import com.sebar.Medical.model.dto.FacilityDto;
import com.sebar.Medical.model.entity.Facility;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacilityMapper {
    Facility toEntity(FacilityCreationDto facilityCreationDto);

    FacilityDto toDto(Facility facility);
}
