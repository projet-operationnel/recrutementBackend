package com.recrutementTuteur.data.repository;

import com.recrutementTuteur.data.entity.AnneeAcademique;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AnneeAcademiqueRepository extends BaseRepository<AnneeAcademique, Long> {
    Optional<AnneeAcademique> findByLibelle(String libelle);
    Optional<AnneeAcademique> findByEstActiveTrue();
}