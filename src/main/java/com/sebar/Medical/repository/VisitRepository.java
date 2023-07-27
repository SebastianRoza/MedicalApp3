package com.sebar.Medical.repository;

import com.sebar.Medical.model.entity.Patient;
import com.sebar.Medical.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findByVisitTime(LocalDateTime visitTime);

    Optional<Visit> findById(Long id);

    List<Visit> findByPatient(Patient Patient);

    @Query("SELECT v " +
            "FROM Visit v " +
            "WHERE v.visitTime <= :endVisitTime " +
            "AND v.endVisitTime >= :startVisitTime")
    List<Visit> findAllOverlapping(LocalDateTime startVisitTime, LocalDateTime endVisitTime);

    @Query("SELECT v.patient " +
            "FROM Visit v " +
            "WHERE v.doctor.id = :doctorId"
    )
    List<Patient> findDoctorPatients(Long doctorId);
}
