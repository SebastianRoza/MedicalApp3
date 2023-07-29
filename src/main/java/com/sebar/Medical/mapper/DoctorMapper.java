package com.sebar.Medical.mapper;

import com.sebar.Medical.model.dto.DoctorCreationDto;
import com.sebar.Medical.model.dto.DoctorDTO;
import com.sebar.Medical.model.entity.Doctor;
import com.sebar.Medical.model.entity.Facility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mapping(target = "facilitiesId", source = "facilities", qualifiedByName = "mapFacilities")
    DoctorDTO toDto(Doctor doctor);

    Doctor toEntity(DoctorCreationDto doctorCreationDto);
    @Named("mapFacilities")
    default List<Long> facilitiesToFacilitiesId(List<Facility> facilities) {
        if (facilities == null){
            return null;
        }
        return facilities.stream()
                .map(Facility::getId)
                .collect(Collectors.toList());
    }
}
