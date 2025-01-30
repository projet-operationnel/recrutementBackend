package com.recrutementTuteur.web.dto.response;

import com.recrutementTuteur.web.dto.response.ResponseData.LoginData;
import com.recrutementTuteur.web.dto.response.ResponseData.ResponseData;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponse extends ResponseData<LoginData> {
    private int statusCode;

    public LoginResponse(String message, LoginData data, boolean status) {
        super(message, data, status);
        this.statusCode = status ? 200 : 401; // 200 pour succès, 401 pour échec d'authentification
    }

    public LoginResponse(String message, LoginData data, boolean status, int statusCode) {
        super(message, data, status);
        this.statusCode = statusCode;
    }
}
