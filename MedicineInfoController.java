package com.md.prescriptionparser.Controller;

import com.md.prescriptionparser.entity.MedicineInfo;
import com.md.prescriptionparser.service.MedicineInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicine")
@CrossOrigin
public class MedicineInfoController {

    private final MedicineInfoService medicineInfoService;

    public MedicineInfoController(MedicineInfoService medicineInfoService) {
        this.medicineInfoService = medicineInfoService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getMedicineInfo(@PathVariable String name) {

        MedicineInfo medicine = medicineInfoService.findMedicine(name);

        if (medicine == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(medicine);
    }
}