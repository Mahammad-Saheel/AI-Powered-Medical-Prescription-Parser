package com.md.prescriptionparser.service;

import com.md.prescriptionparser.entity.Medicine;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ParserService {

    // -------------------------
    // Doctor Name
    // -------------------------
    public String extractDoctorName(String text) {

        Pattern pattern = Pattern.compile(
                "(?i)([A-Z][A-Z\\s.,]+(?:M\\.D\\.|MD))"
        );

        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {

            return matcher.group(1)
                    .replaceFirst("(?i)^fen\\s*", "")
                    .replace("|", "")
                    .replace("_", "")
                    .trim();
        }

        return "Unknown";
    }

    // -------------------------
    // Doctor Specialization
    // -------------------------
    public String extractDoctorSpecialization(String text) {

        Pattern pattern = Pattern.compile(
                "(?i)(General Surgeon|Cardiologist|Dermatologist|Neurologist|Orthopedic Surgeon|Pediatrician|Physician|Gynecologist|Psychiatrist|Urologist)"
        );

        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return "Unknown";
    }

    // -------------------------
    // Patient Name
    // -------------------------
    public String extractPatientName(String text) {

        Pattern pattern =
                Pattern.compile("(?i)Name\\s*:?\\s*([^\\r\\n]+)");

        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {

            return matcher.group(1)
                    .replaceAll("(?i)sex.*", "")
                    .replaceAll("(?i)address.*", "")
                    .replaceAll("(?i)date.*", "")
                    .replaceAll("(?i)Ave.*", "")
                    .replaceAll("\\d+", "")
                    .replaceAll("_+", "")
                    .replace("|", "")
                    .trim();
        }

        return "Unknown";
    }

    // -------------------------
    // Prescription Date
    // -------------------------
    public String extractPrescriptionDate(String text) {

        Pattern pattern = Pattern.compile(
                "(?i)Date\\s*:?\\s*([0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4})"
        );

        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return "Unknown";
    }

    // -------------------------
    // Hospital Name
    // -------------------------
    public String extractHospitalName(String text) {

        String[] lines = text.split("\\r?\\n");

        Pattern hospitalPattern = Pattern.compile(
                "([A-Za-z0-9\\s&'.-]+?(?:Medical Center|Hospital))",
                Pattern.CASE_INSENSITIVE
        );

        for (String rawLine : lines) {

            String line = rawLine
                    .replace("‘", "")
                    .replace("|", "")
                    .replace("_", "")
                    .replace("(", " ")
                    .replace(")", " ")
                    .replaceAll("\\s+", " ")
                    .trim();

            if (line.isBlank()) {
                continue;
            }

            String lower = line.toLowerCase();

            // Skip heading
            if (lower.contains("clinic affiliated hospitals")
                    || lower.contains("clinic affiliated hospital")) {
                continue;
            }

            // Fix OCR typo
            line = line.replaceAll("(?i)Medial Center", "Medical Center");

            Matcher matcher = hospitalPattern.matcher(line);

            while (matcher.find()) {

                String hospital = matcher.group(1).trim();

                // Remove OCR junk at beginning
                hospital = hospital.replaceFirst("^[^A-Za-z]+", "");
                hospital = hospital.replaceFirst("^(Cn|Ci|Snes|oon)\\s+", "");

                // Prefer known hospitals if present
                if (hospital.contains("Southwestern Medical Center")) {
                    return "Southwestern Medical Center";
                }

                if (hospital.contains("OSPA Farms Medical Center")) {
                    return "OSPA Farms Medical Center";
                }

                if (hospital.contains("ARC Hospital")) {
                    return "ARC Hospital";
                }

                return hospital;
            }
        }

        return "Unknown";
    }

    // -------------------------
    // Medicines
    // -------------------------
    public List<Medicine> extractMedicines(String text) {

        List<Medicine> medicines = new ArrayList<>();

        String[] lines = text.split("\\r?\\n");

        Pattern medicinePattern = Pattern.compile(
                "^\\s*(\\d+)\\.\\s*(.*?)\\s*(tab|tablet|capsule|cap)\\s*#\\s*(\\d+)",
                Pattern.CASE_INSENSITIVE
        );

        Pattern sigPattern = Pattern.compile(
                "^\\s*Sig\\s*:?\\s*(.*)",
                Pattern.CASE_INSENSITIVE
        );

        Medicine currentMedicine = null;

        for (String raw : lines) {

            String line = raw.trim();

            Matcher medMatcher = medicinePattern.matcher(line);

            if (medMatcher.find()) {

                currentMedicine = new Medicine();

                String medicineName = medMatcher.group(2)
                        .replace("|", "")
                        .replace("_", "")
                        .trim();

                String form = medMatcher.group(3).trim().toLowerCase();
                String quantity = medMatcher.group(4).trim();

                currentMedicine.setMedicineName(medicineName);
                currentMedicine.setForm(form);
                currentMedicine.setQuantity(quantity);

                // Strength
                Matcher strengthMatcher = Pattern.compile(
                        "(\\d+\\s*(?:mg|mcg|g|ml))",
                        Pattern.CASE_INSENSITIVE
                ).matcher(medicineName);

                if (strengthMatcher.find()) {
                    currentMedicine.setStrength(
                            strengthMatcher.group(1).replaceAll("\\s+", "")
                    );
                }

                medicines.add(currentMedicine);
                continue;
            }

            Matcher sigMatcher = sigPattern.matcher(line);

            if (sigMatcher.find() && currentMedicine != null) {

                String dosage = sigMatcher.group(1)
                        .replace("|", "")
                        .replace("_", "")
                        .trim();

                currentMedicine.setDosage(dosage);

                // Frequency
                Matcher frequencyMatcher = Pattern.compile(
                        "(once\\s*a\\s*day|twice\\s*a\\s*day|\\d+x\\s*a\\s*day)",
                        Pattern.CASE_INSENSITIVE
                ).matcher(dosage);

                if (frequencyMatcher.find()) {
                    currentMedicine.setFrequency(
                            frequencyMatcher.group(1).trim()
                    );
                }

                // Duration
                Matcher durationMatcher = Pattern.compile(
                        "(\\d+\\s+(?:day|days|week|weeks|month|months))",
                        Pattern.CASE_INSENSITIVE
                ).matcher(dosage);

                if (durationMatcher.find()) {

                    String duration = durationMatcher.group(1).trim();

                    duration = duration.replaceAll("\\b1 weeks\\b", "1 week");
                    duration = duration.replaceAll("\\b1 months\\b", "1 month");
                    duration = duration.replaceAll("\\b2 week\\b", "2 weeks");
                    duration = duration.replaceAll("\\b3 week\\b", "3 weeks");
                    duration = duration.replaceAll("\\b4 week\\b", "4 weeks");

                    currentMedicine.setDuration(duration);
                }
            }
        }

        return medicines;
    }
}