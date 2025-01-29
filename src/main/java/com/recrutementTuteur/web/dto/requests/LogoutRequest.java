package com.recrutementTuteur.web.dto.requests;

import lombok.Data;

@Data
public class LogoutRequest {
    private String token;
}