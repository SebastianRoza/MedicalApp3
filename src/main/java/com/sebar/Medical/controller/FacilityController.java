package com.sebar.Medical.controller;

import com.sebar.Medical.model.dto.DoctorDTO;
import com.sebar.Medical.model.dto.FacilityCreationDto;
import com.sebar.Medical.model.dto.FacilityDto;
import com.sebar.Medical.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facilities")
public class FacilityController {
    private final FacilityService facilityService;

    @PostMapping
    public FacilityDto addFacility(@RequestBody FacilityCreationDto facilityCreationDto) {
        return facilityService.addFacility(facilityCreationDto);
    }

    @GetMapping
    public List<FacilityDto> showAllFacilities() {
        return facilityService.showAllFacilities();
    }

    @GetMapping("/{facilityId}/doctors")
    public List<DoctorDTO> showDoctorsAssignedToFacility(@PathVariable Long facilityId) {
        return facilityService.showDoctorsAssignedToFacility(facilityId);
    }
}
