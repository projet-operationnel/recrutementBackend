package com.recrutementTuteur.validator;


import com.recrutementTuteur.data.repository.UserRepository;
import com.recrutementTuteur.web.dto.requests.RegisterRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class RegisterRequestValidator implements Validator {
    private final UserRepository userRepository;

    public RegisterRequestValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        validate((RegisterRequest) target, errors, userRepository);
    }

    public void validate(RegisterRequest request, Errors errors, UserRepository userRepository) {
        // Name validation
        if (request.getNom().length() < 1) {
            errors.rejectValue("nom", "nom.length", "Le nom doit contenir au moins 1 caractère");
        }
        if (request.getPrenom().length() < 1) {
            errors.rejectValue("prenom", "prenom.length", "Le prénom doit contenir au moins 1 caractère");
        }

        // Email validation
        if (!isValidEmail(request.getEmail())) {
            errors.rejectValue("email", "email.format", "L'email n'est pas valide");
        } else if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            errors.rejectValue("email", "email.unique", "L'email est déjà utilisé");
        }

        // Password validation
        if (request.getPassword().length() < 4 || !containsUppercase(request.getPassword())) {
            errors.rejectValue("password", "password.format", "Le mot de passe doit contenir au moins 4 caractères et une majuscule");
        }

        // Telephone validation
        if (request.getTelephone().length() != 9 || !startsWithValidPrefix(request.getTelephone())) {
            errors.rejectValue("telephone", "telephone.format", "Le numéro de téléphone doit contenir 9 chiffres et commencer par 77, 76, 78 ou 75");
        } else if (userRepository.findByTelephone(request.getTelephone()).isPresent()) {
            errors.rejectValue("telephone", "telephone.unique", "Le numéro de téléphone est déjà utilisé");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean containsUppercase(String password) {
        return Pattern.compile("[A-Z]").matcher(password).find();
    }

    private boolean startsWithValidPrefix(String telephone) {
        return telephone.startsWith("77") || telephone.startsWith("76") || telephone.startsWith("78") || telephone.startsWith("75");
    }
}