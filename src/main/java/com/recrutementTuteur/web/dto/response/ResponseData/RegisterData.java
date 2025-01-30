package com.recrutementTuteur.web.dto.response.ResponseData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterData {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
}