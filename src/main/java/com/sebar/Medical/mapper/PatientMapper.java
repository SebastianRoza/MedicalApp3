package com.sebar.Medical.mapper;

import com.sebar.Medical.dto.PatientCreationDTO;
import com.sebar.Medical.dto.PatientDTO;
import com.sebar.Medical.dto.PatientEditDTO;
import com.sebar.Medical.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO toDto(Patient patient);
    PatientCreationDTO toCreationDto(Patient patient);
    Patient toEntity(PatientCreationDTO patientCreationDTO);


}
