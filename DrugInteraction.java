package com.md.prescriptionparser.dto;

public class DrugInteraction {

    private String drug1;
    private String drug2;
    private String severity;
    private String message;

    public DrugInteraction() {
    }

    public DrugInteraction(String drug1, String drug2, String severity, String message) {
        this.drug1 = drug1;
        this.drug2 = drug2;
        this.severity = severity;
        this.message = message;
    }

    public String getDrug1() {
        return drug1;
    }

    public void setDrug1(String drug1) {
        this.drug1 = drug1;
    }

    public String getDrug2() {
        return drug2;
    }

    public void setDrug2(String drug2) {
        this.drug2 = drug2;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}