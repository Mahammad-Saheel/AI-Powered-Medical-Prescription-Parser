package com.md.prescriptionparser.service;

import com.md.prescriptionparser.entity.MedicineInfo;
import com.md.prescriptionparser.repository.MedicineInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicineInfoService {

    private final MedicineInfoRepository repository;

    public MedicineInfoService(MedicineInfoRepository repository) {
        this.repository = repository;
    }

    public MedicineInfo findMedicine(String medicineName) {

        if (medicineName == null || medicineName.isBlank()) {
            return null;
        }

        // Remove strengths like "500mg", "400mcg", etc.
        String cleanedName = medicineName
                .replaceAll("(?i)\\b\\d+\\s*(mg|mcg|g|ml)\\b", "")
                .trim();

        Optional<MedicineInfo> medicine =
                repository.findByMedicineNameContainingIgnoreCase(cleanedName);

        return medicine.orElse(null);
    }
}