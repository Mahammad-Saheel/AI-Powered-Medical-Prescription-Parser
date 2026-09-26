package com.md.prescriptionparser.Controller;

import com.md.prescriptionparser.dto.DrugInteraction;
import com.md.prescriptionparser.dto.PrescriptionResponse;
import com.md.prescriptionparser.entity.Medicine;
import com.md.prescriptionparser.entity.Prescription;
import com.md.prescriptionparser.repository.PrescriptionRepository;
import com.md.prescriptionparser.service.DrugInteractionService;
import com.md.prescriptionparser.service.OcrService;
import com.md.prescriptionparser.service.ParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private OcrService ocrService;

    @Autowired
    private ParserService parserService;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private DrugInteractionService drugInteractionService;

    @PostMapping
    public PrescriptionResponse uploadImage(@RequestParam("file") MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Please upload a valid prescription image.");
        }

        try {

            // ===============================
            // Create Upload Folder
            // ===============================

            String uploadPath =
                    System.getProperty("user.dir")
                            + File.separator
                            + "uploads";

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {

                boolean created = uploadDir.mkdirs();

                if (!created) {
                    throw new RuntimeException(
                            "Unable to create uploads directory : "
                                    + uploadDir.getAbsolutePath()
                    );
                }
            }

            // ===============================
            // Generate Unique Filename
            // ===============================

            String originalName = file.getOriginalFilename();

            if (originalName == null || originalName.isBlank()) {
                originalName = "prescription.png";
            }

            String fileName =
                    UUID.randomUUID() + "_" + originalName;

            File imageFile =
                    new File(uploadPath, fileName);

            // ===============================
            // Debug Logs
            // ===============================

            System.out.println("=========================================");
            System.out.println("Project Folder : " + System.getProperty("user.dir"));
            System.out.println("Upload Folder  : " + uploadDir.getAbsolutePath());
            System.out.println("Saving File To : " + imageFile.getAbsolutePath());
            System.out.println("=========================================");

            // ===============================
            // Save Uploaded File
            // ===============================

            file.transferTo(imageFile);

            if (!imageFile.exists()) {
                throw new RuntimeException("Uploaded file was not saved.");
            }

            // ===============================
            // OCR
            // ===============================

            String extractedText =
                    ocrService.extractText(imageFile);

            // ===============================
            // Create Prescription
            // ===============================

            Prescription prescription = new Prescription();

            prescription.setDoctorName(
                    parserService.extractDoctorName(extractedText)
            );

            prescription.setDoctorSpecialization(
                    parserService.extractDoctorSpecialization(extractedText)
            );

            prescription.setHospitalName(
                    parserService.extractHospitalName(extractedText)
            );

            prescription.setPrescriptionDate(
                    parserService.extractPrescriptionDate(extractedText)
            );

            prescription.setPatientName(
                    parserService.extractPatientName(extractedText)
            );

            prescription.setExtractedText(extractedText);

            // Save image filename
            prescription.setImagePath(fileName);

            // ===============================
            // Medicines
            // ===============================

            List<Medicine> medicines =
                    parserService.extractMedicines(extractedText);

            for (Medicine medicine : medicines) {
                prescription.addMedicine(medicine);
            }

            // ===============================
            // Save to Database
            // ===============================

            Prescription savedPrescription =
                    prescriptionRepository.save(prescription);

            // ===============================
            // Drug Interactions
            // ===============================

            List<DrugInteraction> interactions =
                    drugInteractionService.checkInteractions(
                            savedPrescription.getMedicines()
                    );

            // ===============================
            // Return Response
            // ===============================

            return new PrescriptionResponse(
                    savedPrescription,
                    interactions
            );

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to process prescription: " + e.getMessage(),
                    e
            );
        }
    }
}