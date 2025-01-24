package com.recrutementTuteur.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String message;
    private int statusCode;

    // Ajout de la propriété errors pour gérer les erreurs de validation
    private Map<String, String> errors;
}