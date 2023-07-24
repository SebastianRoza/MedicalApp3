package com.sebar.Medical.service;

import com.sebar.Medical.exception.DoctorException;
import com.sebar.Medical.exception.FacilityException;
import com.sebar.Medical.exception.IllegalDoctorDataException;
import com.sebar.Medical.mapper.DoctorMapper;
import com.sebar.Medical.model.dto.DoctorCreationDto;
import com.sebar.Medical.model.dto.DoctorDTO;
import com.sebar.Medical.model.entity.Doctor;
import com.sebar.Medical.model.entity.Facility;
import com.sebar.Medical.repository.DoctorRepository;
import com.sebar.Medical.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorMapper doctorMapper;
    private final DoctorRepository doctorRepository;
    private final FacilityRepository facilityRepository;

    public DoctorDTO addDoctor(DoctorCreationDto doctorCreationDto) {
        if (doctorCreationDto.getEmail() == null) {
            throw new IllegalDoctorDataException("Email can not be null");
        }
        if (doctorRepository.findByEmail(doctorCreationDto.getEmail()).isPresent()) {
            throw new DoctorException("Doctor with this email already exist");
        }
        Doctor doctor = doctorMapper.toEntity(doctorCreationDto);
        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    public String assignFacilityToDoctor(Long doctorId, Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new FacilityException("Facility with this id does not exist"));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorException("Doctor with this id does not exist"));
        if (doctorRepository.isDoctorAlreadyInFacility(doctorId, facilityId)) {
            throw new DoctorException("Such a doctor is already in this facility");
        }
        doctor.getFacilitiesList().add(facility);
        doctorRepository.save(doctor);
        return "Facility assigned";
    }
}
