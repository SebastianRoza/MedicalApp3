package com.sebar.Medical.controller;

import com.sebar.Medical.model.dto.PatientCreationDTO;
import com.sebar.Medical.model.dto.PatientDTO;
import com.sebar.Medical.model.dto.PatientEditDTO;
import com.sebar.Medical.service.PatientService;
import org.springframework.web.bind.annotation.*;
import lombok.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @PostMapping
    public PatientDTO addPatient(@RequestBody PatientCreationDTO patientCreationDTO) {
        return patientService.addPatient(patientCreationDTO);
    }

    @GetMapping
    public List<PatientDTO> showAllPatients() {
        return patientService.showAllPatients();
    }

    @GetMapping("/{email}")
    public PatientDTO showPatientByEmail(@PathVariable String email) {
        return patientService.showPatientByEmail(email);
    }

    @DeleteMapping("/{email}")
    public void removePatientByEmail(@PathVariable String email) {
        patientService.removePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public PatientDTO editPatient(@PathVariable String email, @RequestBody PatientEditDTO patientEditDTO) {
        return patientService.editPatient(email, patientEditDTO);
    }

    @PatchMapping("/{email}")
    public void editPassword(@PathVariable String email, @RequestBody String password) {
        patientService.editPassword(email, password);
    }

}
