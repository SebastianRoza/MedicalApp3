package com.sebar.Medical.controller;

import com.sebar.Medical.model.dto.FacilityCreationDto;
import com.sebar.Medical.model.dto.FacilityDto;
import com.sebar.Medical.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facilities")
public class FacilityController {
    private final FacilityService facilityService;

    @PostMapping
    public FacilityDto addFacility(@RequestBody FacilityCreationDto facilityCreationDto) {
        return facilityService.addFacility(facilityCreationDto);
    }
}
