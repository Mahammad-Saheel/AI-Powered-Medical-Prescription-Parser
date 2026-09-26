package com.md.prescriptionparser.dto;

public class DashboardStats {

    private long totalPrescriptions;
    private long totalMedicines;
    private long totalInteractions;

    public DashboardStats() {
    }

    public DashboardStats(long totalPrescriptions,
                          long totalMedicines,
                          long totalInteractions) {
        this.totalPrescriptions = totalPrescriptions;
        this.totalMedicines = totalMedicines;
        this.totalInteractions = totalInteractions;
    }

    public long getTotalPrescriptions() {
        return totalPrescriptions;
    }

    public void setTotalPrescriptions(long totalPrescriptions) {
        this.totalPrescriptions = totalPrescriptions;
    }

    public long getTotalMedicines() {
        return totalMedicines;
    }

    public void setTotalMedicines(long totalMedicines) {
        this.totalMedicines = totalMedicines;
    }

    public long getTotalInteractions() {
        return totalInteractions;
    }

    public void setTotalInteractions(long totalInteractions) {
        this.totalInteractions = totalInteractions;
    }
}