package com.sebar.Medical.controller;

import com.sebar.Medical.model.dto.DoctorDTO;
import com.sebar.Medical.model.dto.FacilityCreationDto;
import com.sebar.Medical.model.dto.FacilityDto;
import com.sebar.Medical.service.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facilities")
public class FacilityController {
    private final FacilityService facilityService;
    @Operation(summary = "Add Facility")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facility added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacilityDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Such a facility assigned",
                    content = @Content)})
    @PostMapping
    public FacilityDto addFacility(@RequestBody FacilityCreationDto facilityCreationDto) {
        return facilityService.addFacility(facilityCreationDto);
    }
    @Operation(summary = "Show all facilities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facility showned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacilityDto.class)) }),
            })
    @GetMapping
    public List<FacilityDto> showAllFacilities() {
        return facilityService.showAllFacilities();
    }
    @Operation(summary = "Show doctors assigned to the facility")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors shown",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Facility not found",
                    content = @Content)})
    @GetMapping("/{facilityId}/doctors")
    public List<DoctorDTO> showDoctorsAssignedToFacility(@PathVariable Long facilityId) {
        return facilityService.showDoctorsAssignedToFacility(facilityId);
    }
}
