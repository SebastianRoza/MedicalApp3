package com.sebar.Medical.repository;

import com.sebar.Medical.model.entity.Patient;
import com.sebar.Medical.model.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    Optional<Visit> findByVisitTime(LocalDateTime visitTime);

    Optional<Visit> findById(Long id);

    List<Visit> findByPatient(Patient Patient);
}
