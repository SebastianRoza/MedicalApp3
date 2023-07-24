package com.sebar.Medical.repository;

import com.sebar.Medical.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findById(long id);

    @Query("SELECT COUNT(d) > 0 " +
            "FROM Doctor d " +
            "JOIN d.facilitiesList c " +
            "WHERE d.id = :doctorId " +
            "AND c.id = :facilityId")
    boolean isDoctorAlreadyInFacility(Long doctorId, Long facilityId);
}
