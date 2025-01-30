package com.recrutementTuteur.services.interfaces;

import com.recrutementTuteur.web.dto.requests.CreateAnnonceRequest;
import com.recrutementTuteur.web.dto.response.AnnonceResponse;
import java.util.List;

public interface IAnnonceRecrutementService {
    AnnonceResponse creer(CreateAnnonceRequest request);
    List<AnnonceResponse> listerTout();
    List<AnnonceResponse> listerParAnnee(Long anneeId);
    AnnonceResponse modifier(Long id, CreateAnnonceRequest request);
    AnnonceResponse supprimer(Long id);
}