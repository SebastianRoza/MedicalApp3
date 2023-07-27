package com.sebar.Medical.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebar.Medical.model.dto.FacilityCreationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class FacilityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addFacility_FacilityDoesNotExist_FacilityAdded() throws Exception {
        FacilityCreationDto facilityCreationDto = FacilityCreationDto.builder()
                .name("ss")
                .build();
        mockMvc.perform(post("/facilities")
                        .content(objectMapper.writeValueAsString(facilityCreationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name").value("ss"));
    }

    @Test
    void showAllFacilities_FacilitiesExist_FacilitiesShown() throws Exception {
        mockMvc.perform(get("/facilities"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("olsztynska"));
    }

    @Test
    void showDoctorsAssignedToFacility_FacilityFound_DoctorsShown() throws Exception {
        mockMvc.perform(get("/facilities/2/doctors"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
