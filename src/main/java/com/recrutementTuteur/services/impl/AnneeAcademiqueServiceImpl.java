package com.recrutementTuteur.services.impl;

import com.recrutementTuteur.data.entity.AnneeAcademique;
import com.recrutementTuteur.data.repository.AnneeAcademiqueRepository;
import com.recrutementTuteur.services.interfaces.IAnneeAcademiqueService;
import com.recrutementTuteur.validator.AnneeRequestValidator;
import com.recrutementTuteur.web.dto.requests.CreateAnneeRequest;
import com.recrutementTuteur.web.dto.response.AnneeResponse;
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

    private final AnneeAcademiqueRepository anneeRepository;
    private final AnneeRequestValidator anneeValidator;

    @Override
    @Transactional
    public AnneeResponse creer(CreateAnneeRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "request");
        anneeValidator.validate(request, errors);

        if (errors.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            errors.getFieldErrors().forEach(error ->
                    validationErrors.put(error.getField(), error.getDefaultMessage())
            );

            return AnneeResponse.builder()
                    .message("Erreurs de validation")
                    .errors(validationErrors)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        AnneeAcademique annee = new AnneeAcademique();
        annee.setLibelle(request.getLibelle());
        annee.setDateDebut(request.getDateDebut());
        annee.setDateFin(request.getDateFin());
        annee.setEstActive(false);

        AnneeAcademique savedAnnee = anneeRepository.save(annee);

        return AnneeResponse.builder()
                .id(savedAnnee.getId())
                .libelle(savedAnnee.getLibelle())
                .dateDebut(savedAnnee.getDateDebut())
                .dateFin(savedAnnee.getDateFin())
                .estActive(savedAnnee.isEstActive())
                .message("Année académique créée avec succès")
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    @Transactional
    public AnneeResponse activer(Long id) {
        var anneeOpt = anneeRepository.findById(id);

        if (anneeOpt.isEmpty()) {
            return AnneeResponse.builder()
                    .message("Année académique non trouvée")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        var anneeActive = anneeRepository.findByEstActiveTrue();
        anneeActive.ifPresent(a -> {
            a.setEstActive(false);
            anneeRepository.save(a);
        });

        var annee = anneeOpt.get();
        annee.setEstActive(true);
        anneeRepository.save(annee);

        return AnneeResponse.builder()
                .id(annee.getId())
                .libelle(annee.getLibelle())
                .dateDebut(annee.getDateDebut())
                .dateFin(annee.getDateFin())
                .estActive(true)
                .message("Année académique activée avec succès")
                .statusCode(HttpStatus.OK.value())
                .build();
    }


    @Override
    public List<AnneeResponse> listerTout() {
        List<AnneeAcademique> annees = anneeRepository.findAll();
        return annees.stream()
                .map(annee -> AnneeResponse.builder()
                        .id(annee.getId())
                        .libelle(annee.getLibelle())
                        .dateDebut(annee.getDateDebut())
                        .dateFin(annee.getDateFin())
                        .estActive(annee.isEstActive())
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public AnneeResponse modifier(Long id, CreateAnneeRequest request) {
        var anneeOpt = anneeRepository.findById(id);

        if (anneeOpt.isEmpty()) {
            return AnneeResponse.builder()
                    .message("Année académique non trouvée")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        Errors errors = new BeanPropertyBindingResult(request, "request");
        anneeValidator.validate(request, errors);

        if (errors.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            errors.getFieldErrors().forEach(error ->
                    validationErrors.put(error.getField(), error.getDefaultMessage())
            );

            return AnneeResponse.builder()
                    .message("Erreurs de validation")
                    .errors(validationErrors)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        var annee = anneeOpt.get();
        annee.setLibelle(request.getLibelle());
        annee.setDateDebut(request.getDateDebut());
        annee.setDateFin(request.getDateFin());

        AnneeAcademique savedAnnee = anneeRepository.save(annee);

        return AnneeResponse.builder()
                .id(savedAnnee.getId())
                .libelle(savedAnnee.getLibelle())
                .dateDebut(savedAnnee.getDateDebut())
                .dateFin(savedAnnee.getDateFin())
                .estActive(savedAnnee.isEstActive())
                .message("Année académique modifiée avec succès")
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    @Transactional
    public AnneeResponse supprimer(Long id) {
        var anneeOpt = anneeRepository.findById(id);

        if (anneeOpt.isEmpty()) {
            return AnneeResponse.builder()
                    .message("Année académique non trouvée")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        var annee = anneeOpt.get();
        if (annee.isEstActive()) {
            return AnneeResponse.builder()
                    .message("Impossible de supprimer une année académique active")
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        anneeRepository.delete(annee);

        return AnneeResponse.builder()
                .message("Année académique supprimée avec succès")
                .statusCode(HttpStatus.OK.value())
                .build();
    }



}