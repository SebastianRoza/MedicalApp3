package com.sebar.Medical.mapper;

import com.sebar.Medical.model.dto.FacilityCreationDto;
import com.sebar.Medical.model.dto.FacilityDto;
import com.sebar.Medical.model.entity.Doctor;
import com.sebar.Medical.model.entity.Facility;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface FacilityMapper {
    Facility toEntity(FacilityCreationDto facilityCreationDto);

    @Mapping(target = "doctorsId", source = "doctors", qualifiedByName = "mapDoctors")
    FacilityDto toDto(Facility facility);

    @Named("mapDoctors")
    default List<Long> doctorsToDoctorsIds(List<Doctor> doctors) {
        if (doctors == null) {
            return null;
        }
        return doctors.stream()
                .map(Doctor::getId)
                .collect(Collectors.toList());
    }
}
