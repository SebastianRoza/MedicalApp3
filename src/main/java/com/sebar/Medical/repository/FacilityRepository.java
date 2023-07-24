package com.sebar.Medical.repository;

import com.sebar.Medical.model.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Optional<Facility> findById(long id);

    Optional<Facility> findByName(String name);
}
