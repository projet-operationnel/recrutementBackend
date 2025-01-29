package com.recrutementTuteur.services.interfaces;

import com.recrutementTuteur.web.dto.requests.CreateAnneeRequest;
import com.recrutementTuteur.web.dto.response.AnneeResponse;
import java.util.List;

public interface IAnneeAcademiqueService {
    AnneeResponse creer(CreateAnneeRequest request);
    AnneeResponse activer(Long id);
    List<AnneeResponse> listerTout();
    AnneeResponse modifier(Long id, CreateAnneeRequest request);
    AnneeResponse supprimer(Long id);

}