package com.sebar.Medical.service;

import com.sebar.Medical.exception.PatientNotFoundException;
import com.sebar.Medical.exception.VisitException;
import com.sebar.Medical.exception.IllegalVisitDateException;
import com.sebar.Medical.mapper.PatientMapper;
import com.sebar.Medical.mapper.VisitMapper;
import com.sebar.Medical.model.dto.PatientDTO;
import com.sebar.Medical.model.dto.VisitCreationDto;
import com.sebar.Medical.model.dto.VisitDto;
import com.sebar.Medical.model.entity.Patient;
import com.sebar.Medical.model.entity.Visit;
import com.sebar.Medical.repository.PatientRepository;
import com.sebar.Medical.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VisitService {
    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final VisitMapper visitMapper;
    private final PatientMapper patientMapper;

    public VisitDto addVisit(VisitCreationDto visitCreationDto) {
        log.info("Add visit: {} to database",visitCreationDto);
        Optional<Visit> visitOptional = visitRepository.findByVisitTime(visitCreationDto.getVisitTime());
        if (visitCreationDto.getVisitTime() == null) {
            throw new IllegalVisitDateException("Time of the visit is null");
        }
        if (!(visitRepository.findAllOverlapping(visitCreationDto.getVisitTime(), visitCreationDto.getEndVisitTime())).isEmpty()) {
            throw new IllegalVisitDateException("In this time slot, visits are overlapping");
        }
        if (visitOptional.isPresent()) {
            throw new IllegalVisitDateException("In this time slot, visit already exist");
        }
        if (visitCreationDto.getVisitTime().isBefore(LocalDateTime.now())
                || visitCreationDto.getVisitTime().getMinute() % 15 != 0) {
            throw new IllegalVisitDateException("Input date is in the past or you did not insert a full hour");
        }
        log.debug("Possible problems with hour overlapping or existing, new visit time: {}, and end time: {}",visitCreationDto.getVisitTime(),visitCreationDto.getEndVisitTime());
        Visit visit = visitMapper.toEntity(visitCreationDto);
        return visitMapper.toDto(visitRepository.save(visit));
    }

    public List<VisitDto> showAllVisits() {
        return visitRepository.findAll()
                .stream()
                .map(visitMapper::toDto)
                .collect(Collectors.toList());
    }

    public PatientDTO assignPatientToVisit(Long patientId, Long visitId) {
        log.info("Assing patient with this id: {} to bisit with this id: {}",patientId,visitId);
        Visit visit = visitRepository.findById(visitId).orElseThrow(() -> new VisitException("Visit with this id: "+visitId+"not found in database"));
        if (visit.getVisitTime() == null) {
            throw new IllegalVisitDateException("Time of the visit is null: "+visit.getVisitTime());
        }
        if (visit.getVisitTime().isBefore(LocalDateTime.now()) || visit.getPatient() != null) {
            throw new VisitException("Such a visit is not available, you want to assign to visit which was in the past or visit is already assigned");
        }
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient with this id:"+patientId+"not found in database"));
        visit.setPatient(patient);
        visitRepository.save(visit);
        return patientMapper.toDto(patient);
    }

    public List<VisitDto> showPatientVisits(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient with this id:"+patientId+"not found in database"));
        return visitRepository.findByPatient(patient)
                .stream()
                .map(visitMapper::toDto)
                .collect(Collectors.toList());
    }
}
