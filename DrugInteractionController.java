package com.md.prescriptionparser.Controller;

import com.md.prescriptionparser.entity.DrugInteractionEntity;
import com.md.prescriptionparser.repository.DrugInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drug-interactions")
@CrossOrigin
public class DrugInteractionController {

    @Autowired
    private DrugInteractionRepository repository;

    @GetMapping
    public List<DrugInteractionEntity> getAllInteractions() {
        return repository.findAll();
    }

    @PostMapping
    public DrugInteractionEntity addInteraction(
            @RequestBody DrugInteractionEntity interaction) {

        interaction.setDrug1(interaction.getDrug1().toLowerCase().trim());
        interaction.setDrug2(interaction.getDrug2().toLowerCase().trim());

        return repository.save(interaction);
    }
}