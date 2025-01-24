/*
package com.recrutementTuteur.services.interfaces;

import com.recrutementTuteur.web.dto.requests.RegisterRequest;
import com.recrutementTuteur.web.dto.response.RegisterResponse;

public interface IUserService {
    RegisterResponse register(RegisterRequest request);
}*/

package com.recrutementTuteur.services.interfaces;

import com.recrutementTuteur.web.dto.requests.LoginRequest;
import com.recrutementTuteur.web.dto.requests.RegisterRequest;
import com.recrutementTuteur.web.dto.response.LoginResponse;
import com.recrutementTuteur.web.dto.response.RegisterResponse;

public interface IUserService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}