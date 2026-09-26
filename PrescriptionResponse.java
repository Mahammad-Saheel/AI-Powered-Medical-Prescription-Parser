package com.md.prescriptionparser.dto;

import com.md.prescriptionparser.entity.Prescription;

import java.util.List;

public class PrescriptionResponse {

    private Prescription prescription;
    private List<DrugInteraction> interactions;

    public PrescriptionResponse() {
    }

    public PrescriptionResponse(Prescription prescription,
                                List<DrugInteraction> interactions) {
        this.prescription = prescription;
        this.interactions = interactions;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public List<DrugInteraction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<DrugInteraction> interactions) {
        this.interactions = interactions;
    }
}