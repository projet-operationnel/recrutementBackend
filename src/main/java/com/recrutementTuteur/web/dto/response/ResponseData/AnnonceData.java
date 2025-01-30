package com.recrutementTuteur.web.dto.response.ResponseData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnonceData {
    private Long id;
    private String titre;
    private String description;
    private LocalDate datePublication;
    private boolean estActive;
    private Long anneeAcademiqueId;
    private String anneeAcademiqueLibelle;
}