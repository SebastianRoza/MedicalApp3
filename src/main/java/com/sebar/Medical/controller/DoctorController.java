package com.sebar.Medical.controller;

import com.sebar.Medical.model.dto.DoctorCreationDto;
import com.sebar.Medical.model.dto.DoctorDTO;
import com.sebar.Medical.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public String assignFacilityToDoctor(@PathVariable Long doctorId, @RequestParam Long facilityId) {
        return doctorService.assignFacilityToDoctor(doctorId, facilityId);
    }
}
