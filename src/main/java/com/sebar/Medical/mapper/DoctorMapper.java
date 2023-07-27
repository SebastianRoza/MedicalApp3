package com.sebar.Medical.mapper;

import com.sebar.Medical.model.dto.DoctorCreationDto;
import com.sebar.Medical.model.dto.DoctorDTO;
import com.sebar.Medical.model.entity.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorDTO toDto(Doctor doctor);

    Doctor toEntity(DoctorCreationDto doctorCreationDto);
}
