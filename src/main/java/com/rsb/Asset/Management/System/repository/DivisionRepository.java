package com.rsb.Asset.Management.System.repository;

import com.rsb.Asset.Management.System.models.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Integer> {
    Optional<Division> findByDivisionName(String divisionName);
    boolean existsByDivisionName(String divisionName);
}
