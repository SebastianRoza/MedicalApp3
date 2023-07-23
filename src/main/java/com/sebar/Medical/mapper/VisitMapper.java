package com.sebar.Medical.mapper;

import com.sebar.Medical.model.dto.VisitCreationDto;
import com.sebar.Medical.model.dto.VisitDto;
import com.sebar.Medical.model.entity.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PatientMapper.class)
public interface VisitMapper {
    @Mapping(target = "patientId", source = "patient.id")
    VisitDto toDto(Visit visit);

    Visit toEntity(VisitDto visitDto);

    Visit toEntity(VisitCreationDto visitCreationDto);
}
