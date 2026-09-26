package com.md.prescriptionparser.service;

import com.md.prescriptionparser.dto.DrugInteraction;
import com.md.prescriptionparser.entity.DrugInteractionEntity;
import com.md.prescriptionparser.entity.Medicine;
import com.md.prescriptionparser.repository.DrugInteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DrugInteractionService {

    @Autowired
    private DrugInteractionRepository repository;

    public List<DrugInteraction> checkInteractions(List<Medicine> medicines) {

        List<DrugInteraction> interactions = new ArrayList<>();

        for (int i = 0; i < medicines.size(); i++) {

            for (int j = i + 1; j < medicines.size(); j++) {

                String drug1 = normalize(medicines.get(i).getMedicineName());
                String drug2 = normalize(medicines.get(j).getMedicineName());

                Optional<DrugInteractionEntity> interaction =
                        repository.findByDrug1AndDrug2(drug1, drug2);

                if (interaction.isEmpty()) {
                    interaction =
                            repository.findByDrug1AndDrug2(drug2, drug1);
                }

                if (interaction.isPresent()) {

                    DrugInteractionEntity entity = interaction.get();

                    interactions.add(
                            new DrugInteraction(
                                    entity.getDrug1(),
                                    entity.getDrug2(),
                                    entity.getSeverity(),
                                    entity.getMessage()
                            )
                    );
                }
            }
        }

        return interactions;
    }

    private String normalize(String medicineName) {

        if (medicineName == null) {
            return "";
        }

        return medicineName
                .toLowerCase()
                .replaceAll("\\d+\\s*(mg|mcg|g|ml)", "")
                .replaceAll("\\s+", " ")
                .trim();
    }
}