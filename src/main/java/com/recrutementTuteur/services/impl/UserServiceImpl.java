package com.recrutementTuteur.services.impl;

import com.recrutementTuteur.data.entity.User;
import com.recrutementTuteur.data.enums.Role;
import com.recrutementTuteur.data.repository.UserRepository;
import com.recrutementTuteur.services.interfaces.IUserService;
import com.recrutementTuteur.web.dto.requests.RegisterRequest;
import com.recrutementTuteur.web.dto.response.RegisterResponse;
import com.recrutementTuteur.validator.RegisterRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import com.recrutementTuteur.security.jwt.JwtService;
import com.recrutementTuteur.web.dto.requests.LoginRequest;
import com.recrutementTuteur.web.dto.response.LoginResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RegisterRequestValidator registerRequestValidator;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService; // Nouveau service JWT

    @Override
    public RegisterResponse register(RegisterRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "request");
        registerRequestValidator.validate(request, errors, userRepository);

        if (errors.hasErrors()) {
            Map<String, String> validationErrors = new HashMap<>();
            errors.getFieldErrors().forEach(error -> validationErrors.put(error.getField(), error.getDefaultMessage()));

            return RegisterResponse.builder()
                    .message("Validation errors")
                    .errors(validationErrors)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
        }

        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setTelephone(request.getTelephone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CANDIDAT);

        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .nom(savedUser.getNom())
                .prenom(savedUser.getPrenom())
                .email(savedUser.getEmail())
                .message("User registered successfully")
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        // Rechercher l'utilisateur par email
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        // Vérifier si l'utilisateur existe et si le mot de passe est correct
        if (userOptional.isEmpty()) {
            return LoginResponse.builder()
                    .message("Aucun compte associé à cet email")
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }

        User user = userOptional.get();

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return LoginResponse.builder()
                    .message("Mot de passe incorrect")
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }

        // Générer le token JWT
        String token = jwtService.generateToken(user);

        // Connexion réussie avec token
        return LoginResponse.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .token(token) // Ajout du token
                .message("Connexion réussie")
                .statusCode(HttpStatus.OK.value())
                .build();
    }
}