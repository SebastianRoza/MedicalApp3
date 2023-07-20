package com.sebar.Medical.service;

import com.sebar.Medical.exception.VisitException;
import com.sebar.Medical.exception.WrongDateException;
import com.sebar.Medical.mapper.PatientMapper;
import com.sebar.Medical.mapper.VisitMapper;
import com.sebar.Medical.model.dto.PatientDTO;
import com.sebar.Medical.model.dto.VisitCreationDto;
import com.sebar.Medical.model.dto.VisitDto;
import com.sebar.Medical.model.entity.Patient;
import com.sebar.Medical.model.entity.Visit;
import com.sebar.Medical.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final PatientService patientService;
    private final PatientMapper patientMapper;

    public VisitDto addVisit(VisitCreationDto visitCreationDto) {
        Optional<Visit> visitOptional = visitRepository.findByVisitTime(visitCreationDto.getVisitTime());
        if (visitOptional.isPresent()) {
            throw new WrongDateException("In this time slot, visit already exist");
        }
        if (visitCreationDto.getVisitTime().isBefore(LocalDateTime.now())
                || visitCreationDto.getVisitTime().getMinute() != 0
                || visitCreationDto.getVisitTime().getSecond() != 0
                || visitCreationDto.getVisitTime().getNano() != 0) {
            throw new WrongDateException("Input date is in the past or you did not insert a full hour");
        }
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
        Optional<Visit> visitOptional = visitRepository.findById(visitId);
        if (visitOptional.isEmpty() || visitOptional.get().getVisitTime().isBefore(LocalDateTime.now()) || visitOptional.get().getPatient() != null) {
            throw new VisitException("Such a visit do not exist, you want to assign to visit which was in the past or visit is already assigned");
        }
        Patient patient = patientService.getPatientById(patientId);
        visitOptional.get().setPatient(patient);
        visitRepository.save(visitOptional.get());
        return patientMapper.toDto(patient);
    }

    public List<VisitDto> showPatientVisits(Long patientId) {
        return visitRepository.findByPatient(patientService.getPatientById(patientId))
                .stream()
                .map(visitMapper::toDto)
                .collect(Collectors.toList());
    }
}
