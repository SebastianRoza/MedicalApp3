package com.sebar.Medical.controller;

import com.sebar.Medical.model.dto.*;
import com.sebar.Medical.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping
    public DoctorDTO addDoctor(@RequestBody DoctorCreationDto doctorCreationDto) {
        return doctorService.addDoctor(doctorCreationDto);
    }

    @PatchMapping("/{doctorId}")
    public FacilityDto assignFacilityToDoctor(@PathVariable Long doctorId, @RequestParam Long facilityId) {
        return doctorService.assignFacilityToDoctor(doctorId, facilityId);
    }

    @GetMapping
    public List<DoctorDTO> showAllDoctors() {
        return doctorService.showAllDoctors();
    }

    @GetMapping("/{doctorId}/facilities")
    public List<FacilityDto> showAllFacilitiesWithGivenDoctor(@PathVariable Long doctorId) {
        return doctorService.showAllFacilitiesWithGivenDoctor(doctorId);
    }

    @GetMapping("/{doctorId}/visits")
    public List<VisitDto> showAllVisits(@PathVariable Long doctorId) {
        return doctorService.showAllVisits(doctorId);
    }

    @PatchMapping("/{doctorId}/visit")
    public VisitDto assignVisit(@PathVariable Long doctorId, @RequestParam Long visitId) {
        return doctorService.assignVisit(doctorId, visitId);
    }

    @GetMapping("/{doctorId}/patients")
    public List<PatientDTO> getDoctorPatients(@PathVariable Long doctorId) {
        return doctorService.getDoctorPatients(doctorId);
    }
}
