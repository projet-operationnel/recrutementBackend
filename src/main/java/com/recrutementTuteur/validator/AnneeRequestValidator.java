package com.recrutementTuteur.validator;

import com.recrutementTuteur.data.repository.AnneeAcademiqueRepository;
import com.recrutementTuteur.web.dto.requests.CreateAnneeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class AnneeRequestValidator implements Validator {

    private final AnneeAcademiqueRepository anneeRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateAnneeRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateAnneeRequest request = (CreateAnneeRequest) target;

        // Validation du libellé
        if (anneeRepository.findByLibelle(request.getLibelle()).isPresent()) {
            errors.rejectValue("libelle", "libelle.unique", "Ce libellé existe déjà");
        }

        // Validation des dates
        if (request.getDateDebut() != null && request.getDateFin() != null) {
            if (request.getDateFin().isBefore(request.getDateDebut())) {
                errors.rejectValue("dateFin", "dates.ordre",
                        "La date de fin doit être postérieure à la date de début");
            }
        }
    }
}