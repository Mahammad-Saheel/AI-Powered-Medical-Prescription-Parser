package com.md.prescriptionparser.repository;

import com.md.prescriptionparser.entity.DrugInteractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrugInteractionRepository
        extends JpaRepository<DrugInteractionEntity, Long> {

    Optional<DrugInteractionEntity> findByDrug1AndDrug2(
            String drug1,
            String drug2
    );
}