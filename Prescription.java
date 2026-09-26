package com.md.prescriptionparser.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "doctor_specialization")
    private String doctorSpecialization;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "prescription_date")
    private String prescriptionDate;

    @Column(name = "patient_name")
    private String patientName;

    // NEW: Store uploaded image path
    @Column(name = "image_path")
    private String imagePath;

    @OneToMany(
            mappedBy = "prescription",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Medicine> medicines = new ArrayList<>();

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String extractedText;

    public Prescription() {
    }

    public Long getId() {
        return id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(String prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    // NEW
    public String getImagePath() {
        return imagePath;
    }

    // NEW
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines.clear();

        if (medicines != null) {
            medicines.forEach(this::addMedicine);
        }
    }

    public void addMedicine(Medicine medicine) {
        if (medicine != null) {
            medicine.setPrescription(this);
            this.medicines.add(medicine);
        }
    }

    public void removeMedicine(Medicine medicine) {
        if (medicine != null) {
            medicine.setPrescription(null);
            this.medicines.remove(medicine);
        }
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }
}