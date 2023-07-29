package com.sebar.Medical.controller;

import com.sebar.Medical.model.dto.*;
import com.sebar.Medical.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @Operation(summary = "Add doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor assigned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Such a doctor already assigned",
                    content = {@Content(mediaType = "application/json",
                    schema=@Schema(implementation = String.class))
            })})
    @PostMapping
    public DoctorDTO addDoctor(@RequestBody DoctorCreationDto doctorCreationDto) {
        return doctorService.addDoctor(doctorCreationDto);
    }
    @Operation(summary = "Assign the facility to doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facility assigned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacilityDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content = @Content)})
    @PatchMapping("/{doctorId}")
    public FacilityDto assignFacilityToDoctor(@Parameter(description = "id of doctor") @PathVariable Long doctorId, @RequestParam Long facilityId) {
        return doctorService.assignFacilityToDoctor(doctorId, facilityId);
    }
    @Operation(summary = "Show All doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors shown",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DoctorDTO.class)) }),
            })
    @GetMapping
    public List<DoctorDTO> showAllDoctors() {
        return doctorService.showAllDoctors();
    }
    @Operation(summary = "Show facilities with given doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facility shown",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacilityDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content = @Content)})
    @GetMapping("/{doctorId}/facilities")
    public List<FacilityDto> showAllFacilitiesWithGivenDoctor(@PathVariable Long doctorId) {
        return doctorService.showAllFacilitiesWithGivenDoctor(doctorId);
    }
    @Operation(summary = "Show all visits with given doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "doctor found ",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content = @Content)})
    @GetMapping("/{doctorId}/visits")
    public List<VisitDto> showAllVisits(@PathVariable Long doctorId) {
        return doctorService.showAllVisits(doctorId);
    }
    @Operation(summary = "Assign visit to doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor and visit found and visit assigned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Doctor or visit not found",
                    content = @Content)})
    @PatchMapping("/{doctorId}/visit")
    public VisitDto assignVisit(@PathVariable Long doctorId, @RequestParam Long visitId) {
        return doctorService.assignVisit(doctorId, visitId);
    }
    @Operation(summary = "Get doctor's patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor assigned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content = @Content)})
    @GetMapping("/{doctorId}/patients")
    public List<PatientDTO> getDoctorPatients(@PathVariable Long doctorId) {
        return doctorService.getDoctorPatients(doctorId);
    }
}
