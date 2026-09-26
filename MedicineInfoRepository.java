package com.md.prescriptionparser.repository;

import com.md.prescriptionparser.entity.MedicineInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineInfoRepository extends JpaRepository<MedicineInfo, Long> {

    Optional<MedicineInfo> findByMedicineNameContainingIgnoreCase(String medicineName);

}