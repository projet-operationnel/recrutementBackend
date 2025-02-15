package com.recrutementTuteur.web.dto.response;

import com.recrutementTuteur.web.dto.response.ResponseData.AnneeData;
import com.recrutementTuteur.web.dto.response.ResponseData.ResponseData;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@NoArgsConstructor
public class AnneeResponse extends ResponseData<AnneeData> {
    private int statusCode;
    private Map<String, String> errors;

    public AnneeResponse(String message, AnneeData data, boolean status) {
        super(message, data, status);
        this.statusCode = status ? 200 : 400;
    }

    public AnneeResponse(String message, AnneeData data, boolean status, int statusCode) {
        super(message, data, status);
        this.statusCode = statusCode;
    }

    public AnneeResponse(String message, AnneeData data, boolean status, int statusCode, Map<String, String> errors) {
        super(message, data, status);
        this.statusCode = statusCode;
        this.errors = errors;
    }
}