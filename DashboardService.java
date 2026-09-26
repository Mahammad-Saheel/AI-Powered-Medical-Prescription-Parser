package com.md.prescriptionparser.service;

import com.md.prescriptionparser.dto.DashboardStats;
import com.md.prescriptionparser.entity.Medicine;
import com.md.prescriptionparser.entity.Prescription;
import com.md.prescriptionparser.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final PrescriptionRepository prescriptionRepository;

    public DashboardService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    public DashboardStats getStats() {

        long totalPrescriptions = prescriptionRepository.count();

        long totalMedicines = 0;

        List<Prescription> prescriptions =
                prescriptionRepository.findAll();

        for (Prescription prescription : prescriptions) {

            if (prescription.getMedicines() != null) {
                totalMedicines += prescription.getMedicines().size();
            }
        }

        long totalInteractions = 0;

        return new DashboardStats(
                totalPrescriptions,
                totalMedicines,
                totalInteractions
        );
    }
}