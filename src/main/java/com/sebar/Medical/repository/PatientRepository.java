package com.sebar.Medical.repository;

import com.sebar.Medical.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, String> {

    Optional<Patient> findByEmail(String email);

    Optional<Patient> findById(Long id);

}
