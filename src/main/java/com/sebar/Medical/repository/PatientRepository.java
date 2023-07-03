package com.sebar.Medical.repository;

import com.sebar.Medical.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PatientRepository extends JpaRepository<Patient,String> {

    Optional<Patient> findByEmail(String email);

}
