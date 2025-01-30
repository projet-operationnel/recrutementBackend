package com.recrutementTuteur.services.impl;

import com.recrutementTuteur.data.entity.User;
import com.recrutementTuteur.data.enums.Role;
import com.recrutementTuteur.data.repository.UserRepository;
import com.recrutementTuteur.services.interfaces.IUserService;
import com.recrutementTuteur.web.dto.requests.RegisterRequest;
import com.recrutementTuteur.web.dto.response.RegisterResponse;
import com.recrutementTuteur.validator.RegisterRequestValidator;
import com.recrutementTuteur.web.dto.response.ResponseData.LoginData;
import com.recrutementTuteur.web.dto.response.ResponseData.RegisterData;
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
import com.recrutementTuteur.web.dto.requests.LogoutRequest;
import com.recrutementTuteur.web.dto.response.LogoutResponse;


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
            errors.getFieldErrors().forEach(error ->
                    validationErrors.put(error.getField(), error.getDefaultMessage()));

            return new RegisterResponse(
                    "Erreurs de validation",
                    null,
                    false,
                    HttpStatus.BAD_REQUEST.value(),
                    validationErrors
            );
        }

        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setTelephone(request.getTelephone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CANDIDAT);

        User savedUser = userRepository.save(user);

        RegisterData registerData = RegisterData.builder()
                .id(savedUser.getId())
                .nom(savedUser.getNom())
                .prenom(savedUser.getPrenom())
                .email(savedUser.getEmail())
                .build();

        return new RegisterResponse(
                "Inscription réussie",
                registerData,
                true,
                HttpStatus.CREATED.value()
        );
    }
    @Override
    public LoginResponse login(LoginRequest request) {
        // Rechercher l'utilisateur par email
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        // Vérifier si l'utilisateur existe
        if (userOptional.isEmpty()) {
            return new LoginResponse(
                    "Aucun compte associé à cet email",
                    null,
                    false,
                    HttpStatus.UNAUTHORIZED.value()
            );
        }

        User user = userOptional.get();

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse(
                    "Mot de passe incorrect",
                    null,
                    false,
                    HttpStatus.UNAUTHORIZED.value()
            );
        }

        // Générer le token JWT
        String token = jwtService.generateToken(user);

        // Créer l'objet LoginData
        LoginData loginData = LoginData.builder()
                .id(user.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .token(token)
                .build();

        // Retourner la réponse avec succès
        return new LoginResponse(
                "Connexion réussie",
                loginData,
                true,
                HttpStatus.OK.value()
        );
    }

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        try {
            if (request.getToken() == null || request.getToken().isEmpty()) {
                return LogoutResponse.builder()
                        .message("Token invalide")
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build();
            }

            jwtService.invalidateToken(request.getToken());

            return LogoutResponse.builder()
                    .message("Déconnexion réussie")
                    .statusCode(HttpStatus.OK.value())
                    .build();
        } catch (Exception e) {
            return LogoutResponse.builder()
                    .message("Erreur lors de la déconnexion")
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }
}