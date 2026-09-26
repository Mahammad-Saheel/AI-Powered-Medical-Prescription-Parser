package com.md.prescriptionparser.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "drug_interactions")
public class DrugInteractionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String drug1;

    @Column(nullable = false)
    private String drug2;

    private String severity;

    @Column(length = 1000)
    private String message;

    public DrugInteractionEntity() {
    }

    public Long getId() {
        return id;
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