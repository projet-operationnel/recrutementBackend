package com.recrutementTuteur.web.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogoutResponse {
    private String message;
    private int statusCode;
}