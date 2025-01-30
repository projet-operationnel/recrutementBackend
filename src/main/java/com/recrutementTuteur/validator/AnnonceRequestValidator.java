package com.recrutementTuteur.validator;

import com.recrutementTuteur.data.repository.AnneeAcademiqueRepository;
import com.recrutementTuteur.web.dto.requests.CreateAnnonceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class AnnonceRequestValidator implements Validator {

    private final AnneeAcademiqueRepository anneeRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateAnnonceRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateAnnonceRequest request = (CreateAnnonceRequest) target;

        // Validation de l'année académique
        var anneeOpt = anneeRepository.findById(request.getAnneeAcademiqueId());
        if (anneeOpt.isEmpty()) {
            errors.rejectValue("anneeAcademiqueId", "annee.inexistante",
                    "Cette année académique n'existe pas");
        } else if (!anneeOpt.get().isEstActive()) {
            errors.rejectValue("anneeAcademiqueId", "annee.inactive",
                    "L'année académique doit être active");
        }
    }
}