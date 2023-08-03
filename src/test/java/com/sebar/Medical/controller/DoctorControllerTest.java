package com.sebar.Medical.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebar.Medical.model.dto.DoctorCreationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DoctorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addDoctor_DoctorDoesNotExist_DoctorAdded() throws Exception {
        DoctorCreationDto doctorCreationDto = DoctorCreationDto.builder().email("xx").build();
        mockMvc.perform(post("/doctors")
                        .content(objectMapper.writeValueAsString(doctorCreationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.email").value("xx"));
    }

    @Test
    void assignFacilityToDoctor_FacilityNotAssignedToDoctorYetAndDoctorExist_FacilityAssigned() throws Exception {
       Long facilityId=1L;
        mockMvc.perform(patch("/doctors/1")
                        .param("facilityId",facilityId.toString()))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name").value("olsztynska"));

    }

    @Test
    void showAllDoctors_DoctorsExist_DoctorsShown() throws Exception {
        mockMvc.perform(get("/doctors"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email").value("xxx@gmail.com"));
    }

    @Test
    void showAllFacilitiesWithGivenDoctor_DoctorExist_FacilitiesShown() throws Exception {
        mockMvc.perform(get("/doctors/1/facilities"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void showAllVisits_doctorExist_VisitsShown() throws Exception {
        mockMvc.perform(get("/doctors/1/visits"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void assignVisit_DoctorExistAndVisitExist_VisitAssigned() throws Exception {
        Long visitId = 1L;
        mockMvc.perform(patch("/doctors/1/visit")
                        .param("visitId", visitId.toString()))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.doctorId").value("1"));
    }

    @Test
    void getDoctorPatients_DoctorExisT_PatientsShown() throws Exception {
        mockMvc.perform(get("/doctors/1/patients"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
