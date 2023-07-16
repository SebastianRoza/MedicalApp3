package com.sebar.Medical.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebar.Medical.model.dto.PatientCreationDTO;
import com.sebar.Medical.model.dto.PatientEditDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PatientControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPatient_PatientExist_PatientReturned() throws Exception {
        mockMvc.perform(get("/patients/seba.r@gmail.com"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.email").value("seba.r@gmail.com"));
    }

    @Test
    void showAllPatients_PatientsExists_PatientsReturned() throws Exception {
        mockMvc.perform(get("/patients"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].email").value("seba.r@gmail.com"));
    }

    @Test
    @Rollback
    void removePatient_PatientFound_PatientRemoved() throws Exception {
        mockMvc.perform(delete("/patients/marek.k@gmail.com"))
                .andExpect(status().is(200));
    }

    @Test
    @Rollback
    void editPatient_PatientToEditFound_DataEdited() throws Exception {
        PatientEditDTO patientEditDTO = PatientEditDTO.builder()
                .email("test.com")
                .firstName("dd")
                .lastName("fd")
                .birthday(LocalDate.EPOCH)
                .password("dd")
                .phoneNumber("3232")
                .build();
        mockMvc.perform(put("/patients/marek.k@gmail.com")
                        .content(objectMapper.writeValueAsString(patientEditDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.firstName").value("dd"))
                .andExpect(jsonPath("$.lastName").value("fd"));
    }

    @Test
    @Rollback
    void editPassword_PatientFound_PasswordOfPatientChanged() throws Exception {
        mockMvc.perform(patch("/patients/marek.k@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("dd"))
                .andDo(print())
                .andExpect(status().is(200));
    }

    @Test
    @Rollback
    void addPatient_DataNotNull_PatientAdded() throws Exception {
        PatientCreationDTO patientCreationDTO = PatientCreationDTO.builder()
                .email("sds")
                .password("s")
                .idCardNo("d3")
                .firstName("f")
                .lastName("fd")
                .phoneNumber("f34")
                .birthday(LocalDate.EPOCH)
                .build();
        mockMvc.perform(post("/patients")
                        .content(objectMapper.writeValueAsString(patientCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.email").value("sds"));
    }
}
