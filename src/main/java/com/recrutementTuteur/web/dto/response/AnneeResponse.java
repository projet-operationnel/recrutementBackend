package com.recrutementTuteur.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnneeResponse {
    private Long id;
    private String libelle;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private boolean estActive;
    private String message;
    private int statusCode;
    private Map<String, String> errors;
}