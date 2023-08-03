package com.sebar.Medical.mapper;

import com.sebar.Medical.model.dto.VisitCreationDto;
import com.sebar.Medical.model.dto.VisitDto;
import com.sebar.Medical.model.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = PatientMapper.class)
public interface VisitMapper {
    @Mappings({
            @Mapping(target = "patientId", source = "patient.id"),
            @Mapping(target = "doctorId", source = "doctor.id")
    })
    VisitDto toDto(Visit visit);

    Visit toEntity(VisitDto visitDto);

    Visit toEntity(VisitCreationDto visitCreationDto);
}
