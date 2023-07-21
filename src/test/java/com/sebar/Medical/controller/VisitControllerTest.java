package com.sebar.Medical.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebar.Medical.model.dto.VisitCreationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class VisitControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Rollback
    void addVisit_SuchaAVisitDoesNotExist_VisitAdded() throws Exception {
        VisitCreationDto visitCreationDto = VisitCreationDto.builder()
                .visitTime(LocalDateTime.of(2035, 12, 12, 12, 00))
                .build();
        mockMvc.perform(post("/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitCreationDto)))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.visitTime").value("2035-12-12T12:00:00"));

    }

    @Test
    void showAllVisits_visitsExists_VisitsDtoReturned() throws Exception {
        mockMvc.perform(get("/visits"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].visitTime").value("2034-12-12T12:00:00"));
    }

    @Test
    void assignPatientToVisit_patientFoundAndVisitExistAndIsFree_PatientAssigned() throws Exception {
        mockMvc.perform(patch("/visits/{visitId}/{patientId}", 1, 1)
                        .content("{\"key\":\"value\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.email").value("seba.r@gmail.com"));
    }

    @Test
    void showPatientVisits_patientFound_visitsShown() throws Exception {
        mockMvc.perform(get("/visits/{patientId}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].visitTime").value("2031-12-12T14:00:00"));
    }
}
