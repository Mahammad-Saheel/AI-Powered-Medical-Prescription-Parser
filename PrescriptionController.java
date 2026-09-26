package com.md.prescriptionparser.Controller;

import com.md.prescriptionparser.entity.Prescription;
import com.md.prescriptionparser.service.PrescriptionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin
public class PrescriptionController {

    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    // -------------------------
    // Get all prescriptions
    // -------------------------
    @GetMapping
    public List<Prescription> getAll() {
        return service.getAll();
    }

    // -------------------------
    // Get prescription by ID
    // -------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getById(@PathVariable Long id) {

        Prescription prescription = service.getById(id);

        if (prescription == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(prescription);
    }

    @GetMapping("/page")
    public Page<Prescription> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getPage(page, size);
    }

    // -------------------------
    // Search by patient name
    // -------------------------
    @GetMapping("/search/patient/{name}")
    public List<Prescription> searchByPatient(@PathVariable String name) {
        return service.searchByPatientName(name);
    }

    // -------------------------
    // Search by doctor name
    // -------------------------
    @GetMapping("/search/doctor/{name}")
    public List<Prescription> searchByDoctor(@PathVariable String name) {
        return service.searchByDoctorName(name);
    }

    // -------------------------
    // Dashboard Statistics
    // -------------------------

    // Total prescriptions
    @GetMapping("/stats/total")
    public ResponseEntity<Long> getTotalPrescriptions() {
        return ResponseEntity.ok(service.getTotalPrescriptions());
    }

    // Count by doctor name
    @GetMapping("/stats/doctor/{doctorName}")
    public ResponseEntity<Long> getDoctorPrescriptionCount(
            @PathVariable String doctorName) {

        return ResponseEntity.ok(
                service.countByDoctorName(doctorName)
        );
    }

    // Count by patient name
    @GetMapping("/stats/patient/{patientName}")
    public ResponseEntity<Long> getPatientPrescriptionCount(
            @PathVariable String patientName) {

        return ResponseEntity.ok(
                service.countByPatientName(patientName)
        );
    }

    // -------------------------
    // Create new prescription
    // -------------------------
    @PostMapping
    public Prescription create(@RequestBody Prescription prescription) {
        return service.save(prescription);
    }

    // -------------------------
    // Update prescription
    // -------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Prescription> update(
            @PathVariable Long id,
            @RequestBody Prescription updatedPrescription) {

        Prescription updated = service.update(id, updatedPrescription);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated);
    }

    // -------------------------
    // Delete prescription
    // -------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        Prescription existing = service.getById(id);

        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        service.delete(id);

        return ResponseEntity.ok("Prescription deleted successfully.");
    }
}