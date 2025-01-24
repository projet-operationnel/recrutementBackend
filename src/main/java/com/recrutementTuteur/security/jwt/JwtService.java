package com.recrutementTuteur.security.jwt;

import com.recrutementTuteur.data.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    // Default values if not set in properties
    private String jwtSecret = "YourDefaultVeryLongAndComplexSecretKeyThatShouldBeReplacedInProduction";
    private long jwtExpiration = 86400000; // 24 hours

    @Value("${jwt.secret:#{null}}")
    public void setJwtSecret(String secret) {
        if (secret != null) {
            this.jwtSecret = secret;
        }
    }

    @Value("${jwt.expiration:86400000}")
    public void setJwtExpiration(long expiration) {
        this.jwtExpiration = expiration;
    }

    // Générer un token JWT pour un utilisateur
    public String generateToken(User user) {
        // Informations à inclure dans le token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("nom", user.getNom());
        claims.put("prenom", user.getPrenom());
        claims.put("role", user.getRole().name());

        // Construction du token
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Clé de signature pour le token
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}