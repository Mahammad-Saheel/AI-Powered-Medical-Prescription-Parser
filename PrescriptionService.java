package com.md.prescriptionparser.service;

import com.md.prescriptionparser.entity.Medicine;
import com.md.prescriptionparser.entity.Prescription;
import com.md.prescriptionparser.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository repository;

    public PrescriptionService(PrescriptionRepository repository) {
        this.repository = repository;
    }

    // -------------------------
    // Get all prescriptions
    // -------------------------
    public List<Prescription> getAll() {
        return repository.findAll();
    }

    // -------------------------
    // Get prescription by ID
    // -------------------------
    public Prescription getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // -------------------------
    // Search by patient name
    // -------------------------
    public List<Prescription> searchByPatientName(String patientName) {
        return repository.findByPatientNameContainingIgnoreCase(patientName);
    }

    // -------------------------
    // Search by doctor name
    // -------------------------
    public List<Prescription> searchByDoctorName(String doctorName) {
        return repository.findByDoctorNameContainingIgnoreCase(doctorName);
    }

    // -------------------------
    // Dashboard Statistics
    // -------------------------

    // Total prescriptions
    public long getTotalPrescriptions() {
        return repository.count();
    }

    // Count prescriptions by doctor name
    public long countByDoctorName(String doctorName) {
        return repository.countByDoctorNameContainingIgnoreCase(doctorName);
    }

    // Count prescriptions by patient name
    public long countByPatientName(String patientName) {
        return repository.countByPatientNameContainingIgnoreCase(patientName);
    }

    // -------------------------
    // Save prescription
    // -------------------------
    public Prescription save(Prescription prescription) {
        return repository.save(prescription);
    }

    public Page<Prescription> getPage(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    // -------------------------
    // Update prescription
    // -------------------------
    public Prescription update(Long id, Prescription updated) {

        Prescription existing = repository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        // Update prescription details
        existing.setDoctorName(updated.getDoctorName());
        existing.setDoctorSpecialization(updated.getDoctorSpecialization());
        existing.setHospitalName(updated.getHospitalName());
        existing.setPrescriptionDate(updated.getPrescriptionDate());
        existing.setPatientName(updated.getPatientName());
        existing.setExtractedText(updated.getExtractedText());

        // Replace medicines
        existing.getMedicines().clear();

        if (updated.getMedicines() != null) {
            for (Medicine medicine : updated.getMedicines()) {
                existing.addMedicine(medicine);
            }
        }

        return repository.save(existing);
    }

    // -------------------------
    // Delete prescription
    // -------------------------
    public void delete(Long id) {
        repository.deleteById(id);
    }
}