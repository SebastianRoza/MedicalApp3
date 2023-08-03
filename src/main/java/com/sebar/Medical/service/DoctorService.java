package com.sebar.Medical.service;

import com.sebar.Medical.exception.DoctorException;
import com.sebar.Medical.exception.FacilityException;
import com.sebar.Medical.exception.IllegalDoctorDataException;
import com.sebar.Medical.exception.VisitException;
import com.sebar.Medical.mapper.DoctorMapper;
import com.sebar.Medical.mapper.FacilityMapper;
import com.sebar.Medical.mapper.PatientMapper;
import com.sebar.Medical.mapper.VisitMapper;
import com.sebar.Medical.model.dto.*;
import com.sebar.Medical.model.entity.Doctor;
import com.sebar.Medical.model.entity.Facility;
import com.sebar.Medical.model.entity.Visit;
import com.sebar.Medical.repository.DoctorRepository;
import com.sebar.Medical.repository.FacilityRepository;
import com.sebar.Medical.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {
    private final DoctorMapper doctorMapper;
    private final FacilityMapper facilityMapper;
    private final PatientMapper patientMapper;
    private final VisitMapper visitMapper;
    private final DoctorRepository doctorRepository;
    private final FacilityRepository facilityRepository;
    private final VisitRepository visitRepository;

    public DoctorDTO addDoctor(DoctorCreationDto doctorCreationDto) {
        log.info("Process of adding doctor: {} started", doctorCreationDto);
        if (doctorCreationDto.getEmail() == null) {
            log.error("Process of adding doctor: {} failed", doctorCreationDto);
            throw new IllegalDoctorDataException("Email can not be null");
        }
        if (doctorRepository.findByEmail(doctorCreationDto.getEmail()).isPresent()) {
            throw new DoctorException("Doctor with this email:" + doctorCreationDto.getEmail() + " already exist");
        }
        Doctor doctor = doctorMapper.toEntity(doctorCreationDto);
        var result = doctorRepository.save(doctor);
        log.info("Process of adding doctor: {} finished", doctor);
        return doctorMapper.toDto(result);
    }

    public FacilityDto assignFacilityToDoctor(Long doctorId, Long facilityId) {
        log.info("Assign facility with this id:{} to doctor with this id{}", facilityId, doctorId);
        Facility facility = facilityRepository.findById(facilityId).orElseThrow(() -> new FacilityException("Facility with this id does not exist"));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorException("Doctor with this id does not exist"));
        if (doctorRepository.isDoctorAlreadyInFacility(doctorId, facilityId)) {
            throw new DoctorException("Doctor with this id: " + doctorId + " is already in this facility");
        }
        doctor.getFacilities().add(facility);
        doctorRepository.save(doctor);
        return facilityMapper.toDto(facility);
    }

    public List<DoctorDTO> showAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<FacilityDto> showAllFacilitiesWithGivenDoctor(Long doctorId) {
        log.info("show facilities with doctor id: {}", doctorId);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorException("Doctor not found"));
        return doctor.getFacilities().stream()
                .map(facilityMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VisitDto> showAllVisits(Long doctorId) {
        log.info("show visits with doctor id: {}", doctorId);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorException("Doctor not found"));
        return doctor.getVisits().stream()
                .map(visitMapper::toDto)
                .collect(Collectors.toList());
    }

    public VisitDto assignVisit(Long doctorId, Long visitId) {
        log.info("assign visit with id: {} to doctor with id: {}", visitId, doctorId);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorException("Doctor not found"));
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new VisitException("Visit not found"));
        if (visit.getDoctor() != null) {
            throw new VisitException("Visit with this id: " + visitId + " has already assigned Doctor");
        }
        visit.setDoctor(doctor);
        visitRepository.save(visit);
        return visitMapper.toDto(visit);
    }

    public List<PatientDTO> getDoctorPatients(Long doctorId) {
        log.info("show patients with doctor id: {}", doctorId);
        doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorException("Doctor not found"));
        return visitRepository.findDoctorPatients(doctorId).stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }
}
