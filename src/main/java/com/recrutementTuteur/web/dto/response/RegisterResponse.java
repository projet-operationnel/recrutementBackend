package com.recrutementTuteur.web.dto.response;

import com.recrutementTuteur.web.dto.response.ResponseData.RegisterData;
import com.recrutementTuteur.web.dto.response.ResponseData.ResponseData;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@NoArgsConstructor
public class RegisterResponse extends ResponseData<RegisterData> {
    private int statusCode;
    private Map<String, String> errors;

    public RegisterResponse(String message, RegisterData data, boolean status) {
        super(message, data, status);
        this.statusCode = status ? 201 : 400; // 201 pour création réussie, 400 pour erreur de validation
    }

    public RegisterResponse(String message, RegisterData data, boolean status, int statusCode) {
        super(message, data, status);
        this.statusCode = statusCode;
    }

    public RegisterResponse(String message, RegisterData data, boolean status, int statusCode, Map<String, String> errors) {
        super(message, data, status);
        this.statusCode = statusCode;
        this.errors = errors;
    }
}