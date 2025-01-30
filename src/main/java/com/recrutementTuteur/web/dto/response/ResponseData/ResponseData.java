package com.recrutementTuteur.web.dto.response.ResponseData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {
    private String message;
    private T data;
    private boolean status;
}
