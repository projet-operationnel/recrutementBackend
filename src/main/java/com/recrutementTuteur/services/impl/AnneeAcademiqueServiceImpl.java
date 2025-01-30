package com.recrutementTuteur.services.impl;

import com.recrutementTuteur.data.entity.AnneeAcademique;
import com.recrutementTuteur.data.repository.AnneeAcademiqueRepository;
import com.recrutementTuteur.services.interfaces.IAnneeAcademiqueService;
import com.recrutementTuteur.validator.AnneeRequestValidator;
import com.recrutementTuteur.web.dto.requests.CreateAnneeRequest;
import com.recrutementTuteur.web.dto.response.AnneeResponse;
import com.recrutementTuteur.web.dto.response.ResponseData.AnneeData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AnneeAcademiqueServiceImpl implements IAnneeAcademiqueService {

    private final AnneeAcademiqueRepository anneeAcademiqueRepository;
    private final AnneeRequestValidator anneeRequestValidator;

    @Override
    @Transactional
    public AnneeResponse creer(CreateAnneeRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "request");
        anneeRequestValidator.validate(request, errors);

        if (errors.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            errors.getFieldErrors().forEach(error ->
                    validationErrors.put(error.getField(), error.getDefaultMessage())
            );

            return new AnneeResponse(
                    "Erreurs de validation",
                    null,
                    false,
                    HttpStatus.BAD_REQUEST.value(),
                    validationErrors
            );
        }

        AnneeAcademique annee = new AnneeAcademique();
        annee.setLibelle(request.getLibelle());
        annee.setDateDebut(request.getDateDebut());
        annee.setDateFin(request.getDateFin());
        annee.setEstActive(false);

        AnneeAcademique savedAnnee = anneeAcademiqueRepository.save(annee);

        AnneeData anneeData = AnneeData.builder()
                .id(savedAnnee.getId())
                .libelle(savedAnnee.getLibelle())
                .dateDebut(savedAnnee.getDateDebut())
                .dateFin(savedAnnee.getDateFin())
                .estActive(savedAnnee.isEstActive())
                .build();

        return new AnneeResponse(
                "Année académique créée avec succès",
                anneeData,
                true,
                HttpStatus.CREATED.value()
        );
    }

    @Override
    @Transactional
    public AnneeResponse activer(Long id) {
        var anneeOpt = anneeAcademiqueRepository.findById(id);

        if (anneeOpt.isEmpty()) {
            return new AnneeResponse(
                    "Année académique non trouvée",
                    null,
                    false,
                    HttpStatus.NOT_FOUND.value()
            );
        }

        var anneeActive = anneeAcademiqueRepository.findByEstActiveTrue();
        anneeActive.ifPresent(a -> {
            a.setEstActive(false);
            anneeAcademiqueRepository.save(a);
        });

        var annee = anneeOpt.get();
        annee.setEstActive(true);
        anneeAcademiqueRepository.save(annee);

        AnneeData anneeData = AnneeData.builder()
                .id(annee.getId())
                .libelle(annee.getLibelle())
                .dateDebut(annee.getDateDebut())
                .dateFin(annee.getDateFin())
                .estActive(true)
                .build();

        return new AnneeResponse(
                "Année académique activée avec succès",
                anneeData,
                true,
                HttpStatus.OK.value()
        );
    }

    @Override
    public List<AnneeResponse> listerTout() {
        List<AnneeAcademique> annees = anneeAcademiqueRepository.findAll();
        return annees.stream()
                .map(annee -> {
                    AnneeData anneeData = AnneeData.builder()
                            .id(annee.getId())
                            .libelle(annee.getLibelle())
                            .dateDebut(annee.getDateDebut())
                            .dateFin(annee.getDateFin())
                            .estActive(annee.isEstActive())
                            .build();

                    return new AnneeResponse(
                            null,
                            anneeData,
                            true,
                            HttpStatus.OK.value()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AnneeResponse modifier(Long id, CreateAnneeRequest request) {
        var anneeOpt = anneeAcademiqueRepository.findById(id);

        if (anneeOpt.isEmpty()) {
            return new AnneeResponse(
                    "Année académique non trouvée",
                    null,
                    false,
                    HttpStatus.NOT_FOUND.value()
            );
        }

        Errors errors = new BeanPropertyBindingResult(request, "request");
        anneeRequestValidator.validate(request, errors);

        if (errors.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            errors.getFieldErrors().forEach(error ->
                    validationErrors.put(error.getField(), error.getDefaultMessage())
            );

            return new AnneeResponse(
                    "Erreurs de validation",
                    null,
                    false,
                    HttpStatus.BAD_REQUEST.value(),
                    validationErrors
            );
        }

        var annee = anneeOpt.get();
        annee.setLibelle(request.getLibelle());
        annee.setDateDebut(request.getDateDebut());
        annee.setDateFin(request.getDateFin());

        AnneeAcademique savedAnnee = anneeAcademiqueRepository.save(annee);

        AnneeData anneeData = AnneeData.builder()
                .id(savedAnnee.getId())
                .libelle(savedAnnee.getLibelle())
                .dateDebut(savedAnnee.getDateDebut())
                .dateFin(savedAnnee.getDateFin())
                .estActive(savedAnnee.isEstActive())
                .build();

        return new AnneeResponse(
                "Année académique modifiée avec succès",
                anneeData,
                true,
                HttpStatus.OK.value()
        );
    }

    @Override
    @Transactional
    public AnneeResponse supprimer(Long id) {
        var anneeOpt = anneeAcademiqueRepository.findById(id);

        if (anneeOpt.isEmpty()) {
            return new AnneeResponse(
                    "Année académique non trouvée",
                    null,
                    false,
                    HttpStatus.NOT_FOUND.value()
            );
        }

        var annee = anneeOpt.get();
        if (annee.isEstActive()) {
            return new AnneeResponse(
                    "Impossible de supprimer une année académique active",
                    null,
                    false,
                    HttpStatus.BAD_REQUEST.value()
            );
        }

        anneeAcademiqueRepository.delete(annee);

        return new AnneeResponse(
                "Année académique supprimée avec succès",
                null,
                true,
                HttpStatus.OK.value()
        );
    }
}