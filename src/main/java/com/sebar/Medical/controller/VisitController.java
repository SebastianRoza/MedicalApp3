package com.sebar.Medical.controller;

import com.sebar.Medical.model.dto.PatientDTO;
import com.sebar.Medical.model.dto.VisitCreationDto;
import com.sebar.Medical.model.dto.VisitDto;
import com.sebar.Medical.service.VisitService;
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
@RequestMapping("/visits")
public class VisitController {
    private final VisitService visitService;
    @Operation(summary = "Add visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "visit added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Such a visit exist",
                    content = @Content)})
    @PostMapping
    public VisitDto addVisit(@RequestBody VisitCreationDto visitCreationDto) {
        return visitService.addVisit(visitCreationDto);
    }
    @Operation(summary = "Show all visits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "visits shown",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDto.class)) })}
            )
    @GetMapping
    public List<VisitDto> showAllVisits() {
        return visitService.showAllVisits();
    }
    @Operation(summary = "Assign patient to visit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient assgined",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "visit not found",
                    content = @Content)})
    @PatchMapping("/{visitId}")
    public PatientDTO assignPatientToVisit(@RequestParam Long patientId, @PathVariable Long visitId) {
        return visitService.assignPatientToVisit(patientId, visitId);
    }
    @Operation(summary = "Show Patient's visits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "visits shown",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VisitDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Patient not found",
                    content = @Content)})
    @GetMapping("/{patientId}")
    public List<VisitDto> showPatientVisits(@PathVariable Long patientId) {
        return visitService.showPatientVisits(patientId);
    }
}
