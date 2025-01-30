package com.recrutementTuteur.data.repository;

import com.recrutementTuteur.data.entity.AnnonceRecrutement;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnnonceRecrutementRepository extends BaseRepository<AnnonceRecrutement, Long> {
    List<AnnonceRecrutement> findByEstActiveTrue();
    List<AnnonceRecrutement> findByAnneeAcademiqueId(Long anneeId);
}