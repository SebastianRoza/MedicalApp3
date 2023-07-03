package com.sebar.Medical.mapper;

import com.sebar.Medical.model.dto.PatientCreationDTO;
import com.sebar.Medical.model.dto.PatientDTO;
import com.sebar.Medical.model.entity.Patient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDTO toDto(Patient patient);
    Patient toEntity(PatientCreationDTO patientCreationDTO);


}
