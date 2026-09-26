package com.md.prescriptionparser.repository;

import com.md.prescriptionparser.entity.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    // Search by patient name
    List<Prescription> findByPatientNameContainingIgnoreCase(String patientName);

    // Search by doctor name
    List<Prescription> findByDoctorNameContainingIgnoreCase(String doctorName);

    // Statistics
    long countByDoctorNameContainingIgnoreCase(String doctorName);

    long countByPatientNameContainingIgnoreCase(String patientName);

    // Pagination
    Page<Prescription> findAll(Pageable pageable);
}