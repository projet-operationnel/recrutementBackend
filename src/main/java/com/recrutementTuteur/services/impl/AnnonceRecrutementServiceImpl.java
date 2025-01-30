package com.recrutementTuteur.services.impl;

import com.recrutementTuteur.data.entity.AnnonceRecrutement;
import com.recrutementTuteur.data.repository.AnnonceRecrutementRepository;
import com.recrutementTuteur.data.repository.AnneeAcademiqueRepository;
import com.recrutementTuteur.services.interfaces.IAnnonceRecrutementService;
import com.recrutementTuteur.validator.AnnonceRequestValidator;
import com.recrutementTuteur.web.dto.requests.CreateAnnonceRequest;
import com.recrutementTuteur.web.dto.response.AnnonceResponse;
import com.recrutementTuteur.web.dto.response.ResponseData.AnnonceData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnonceRecrutementServiceImpl implements IAnnonceRecrutementService {

    private final AnnonceRecrutementRepository annonceRecrutementRepository;
    private final AnnonceRequestValidator annonceRequestValidator;
    private final AnneeAcademiqueRepository anneeAcademiqueRepository;

    private AnnonceData buildAnnonceData(AnnonceRecrutement annonce) {
        return AnnonceData.builder()
                .id(annonce.getId())
                .titre(annonce.getTitre())
                .description(annonce.getDescription())
                .datePublication(annonce.getDatePublication())
                .estActive(annonce.isEstActive())
                .anneeAcademiqueId(annonce.getAnneeAcademique().getId())
                .anneeAcademiqueLibelle(annonce.getAnneeAcademique().getLibelle())
                .build();
    }

    @Override
    @Transactional
    public AnnonceResponse creer(CreateAnnonceRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "request");
        annonceRequestValidator.validate(request, errors);

        if (errors.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            errors.getFieldErrors().forEach(error ->
                    validationErrors.put(error.getField(), error.getDefaultMessage())
            );

            return new AnnonceResponse(
                    "Erreurs de validation",
                    null,
                    false,
                    HttpStatus.BAD_REQUEST.value(),
                    validationErrors
            );
        }

        var anneeAcademique = anneeAcademiqueRepository.findById(request.getAnneeAcademiqueId())
                .orElseThrow(() -> new RuntimeException("Année académique non trouvée"));

        var annonce = new AnnonceRecrutement();
        annonce.setTitre(request.getTitre());
        annonce.setDescription(request.getDescription());
        annonce.setDatePublication(LocalDate.now());
        annonce.setEstActive(true);
        annonce.setAnneeAcademique(anneeAcademique);

        try {
            var savedAnnonce = annonceRecrutementRepository.save(annonce);
            return new AnnonceResponse(
                    "Annonce créée avec succès",
                    buildAnnonceData(savedAnnonce),
                    true,
                    HttpStatus.CREATED.value()
            );
        } catch (Exception e) {
            return new AnnonceResponse(
                    "Erreur lors de la création de l'annonce: " + e.getMessage(),
                    null,
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    @Override
    public List<AnnonceResponse> listerTout() {
        return annonceRecrutementRepository.findAll().stream()
                .map(annonce -> new AnnonceResponse(
                        null,
                        buildAnnonceData(annonce),
                        true,
                        HttpStatus.OK.value()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnonceResponse> listerParAnnee(Long anneeId) {
        return annonceRecrutementRepository.findByAnneeAcademiqueId(anneeId).stream()
                .map(annonce -> new AnnonceResponse(
                        null,
                        buildAnnonceData(annonce),
                        true,
                        HttpStatus.OK.value()
                ))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AnnonceResponse modifier(Long id, CreateAnnonceRequest request) {
        var annonceOpt = annonceRecrutementRepository.findById(id);

        if (annonceOpt.isEmpty()) {
            return new AnnonceResponse(
                    "Annonce non trouvée",
                    null,
                    false,
                    HttpStatus.NOT_FOUND.value()
            );
        }

        Errors errors = new BeanPropertyBindingResult(request, "request");
        annonceRequestValidator.validate(request, errors);

        if (errors.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            errors.getFieldErrors().forEach(error ->
                    validationErrors.put(error.getField(), error.getDefaultMessage())
            );

            return new AnnonceResponse(
                    "Erreurs de validation",
                    null,
                    false,
                    HttpStatus.BAD_REQUEST.value(),
                    validationErrors
            );
        }

        var anneeAcademique = anneeAcademiqueRepository.findById(request.getAnneeAcademiqueId())
                .orElseThrow(() -> new RuntimeException("Année académique non trouvée"));

        var annonce = annonceOpt.get();

        annonce.setTitre(request.getTitre());
        annonce.setDescription(request.getDescription());
        annonce.setAnneeAcademique(anneeAcademique);

        var savedAnnonce = annonceRecrutementRepository.save(annonce);

        return new AnnonceResponse(
                "Annonce modifiée avec succès",
                buildAnnonceData(savedAnnonce),
                true,
                HttpStatus.OK.value()
        );
    }

    @Override
    @Transactional
    public AnnonceResponse supprimer(Long id) {
        var annonceOpt = annonceRecrutementRepository.findById(id);

        if (annonceOpt.isEmpty()) {
            return new AnnonceResponse(
                    "Annonce non trouvée",
                    null,
                    false,
                    HttpStatus.NOT_FOUND.value()
            );
        }

        annonceRecrutementRepository.delete(annonceOpt.get());

        return new AnnonceResponse(
                "Annonce supprimée avec succès",
                null,
                true,
                HttpStatus.OK.value()
        );
    }
}