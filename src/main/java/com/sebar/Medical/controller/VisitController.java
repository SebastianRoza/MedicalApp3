package com.sebar.Medical.controller;

import com.sebar.Medical.model.dto.PatientDTO;
import com.sebar.Medical.model.dto.VisitCreationDto;
import com.sebar.Medical.model.dto.VisitDto;
import com.sebar.Medical.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {
    private final VisitService visitService;

    @PostMapping
    public VisitDto addVisit(@RequestBody VisitCreationDto visitCreationDto) {
        return visitService.addVisit(visitCreationDto);
    }

    @GetMapping
    public List<VisitDto> showAllVisits() {
        return visitService.showAllVisits();
    }

    @PatchMapping("/{visitId}/{patientId}")
    public PatientDTO assignPatientToVisit(@PathVariable Long patientId, @PathVariable Long visitId) {
        return visitService.assignPatientToVisit(patientId, visitId);
    }

    @GetMapping("/{patientId}")
    public List<VisitDto> showPatientVisits(@PathVariable Long patientId) {
        return visitService.showPatientVisits(patientId);
    }
}
