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
public class LoginResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String token; // Nouveau champ pour le token JWT
    private String message;
    private int statusCode;
    private Map<String, String> errors;
}