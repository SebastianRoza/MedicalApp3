package com.sebar.Medical.controller;

import com.sebar.Medical.model.dto.PatientCreationDTO;
import com.sebar.Medical.model.dto.PatientDTO;
import com.sebar.Medical.model.dto.PatientEditDTO;
import com.sebar.Medical.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import lombok.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;
    @Operation(summary = "Add Patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient added",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Such a patient exist",
                    content = @Content)})
    @PostMapping
    public PatientDTO addPatient(@RequestBody PatientCreationDTO patientCreationDTO) {
        return patientService.addPatient(patientCreationDTO);
    }
    @Operation(summary = "Show all patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients returned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class)) }),
           })
    @GetMapping
    public List<PatientDTO> showAllPatients() {
        return patientService.showAllPatients();
    }
    @Operation(summary = "Show patient by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient shown",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Patient not found",
                    content = @Content)})
    @GetMapping("/{email}")
    public PatientDTO showPatientByEmail(@PathVariable String email) {
        return patientService.showPatientByEmail(email);
    }
    @Operation(summary = "Remove patient by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient removed",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "patient not found",
                    content = @Content)})
    @DeleteMapping("/{email}")
    public void removePatientByEmail(@PathVariable String email) {
        patientService.removePatientByEmail(email);
    }
    @Operation(summary = "Edit patient data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient edited",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Patient not found",
                    content = @Content)})
    @PutMapping("/{email}")
    public PatientDTO editPatient(@PathVariable String email, @RequestBody PatientEditDTO patientEditDTO) {
        return patientService.editPatient(email, patientEditDTO);
    }
    @Operation(summary = "Edit password of the patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password edited",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Patient not found",
                    content = @Content)})
    @PatchMapping("/{email}")
    public void editPassword(@PathVariable String email, @RequestBody String password) {
        patientService.editPassword(email, password);
    }

}
