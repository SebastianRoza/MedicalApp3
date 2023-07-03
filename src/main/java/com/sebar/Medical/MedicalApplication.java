package com.sebar.Medical;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sebar.Medical.model.Patient;
import com.sebar.Medical.service.PatientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.time.LocalDate;
@SpringBootApplication

public class MedicalApplication {

	public static void main(String[] args)  {

		SpringApplication.run(MedicalApplication.class, args);

	}
}
