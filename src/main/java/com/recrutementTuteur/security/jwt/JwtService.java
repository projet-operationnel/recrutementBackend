/*


package com.recrutementTuteur.security.jwt;

import com.recrutementTuteur.data.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtService {
    private String jwtSecret = "YourDefaultVeryLongAndComplexSecretKeyThatShouldBeReplacedInProduction";
    private long jwtExpiration = 86400000; // 24 hours

    // Set pour stocker les tokens invalidés
    private final Set<String> invalidatedTokens = ConcurrentHashMap.newKeySet();

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

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("nom", user.getNom());
        claims.put("prenom", user.getPrenom());
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // Nouvelle méthode pour invalider un token
    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    // Vérifier si un token est invalidé
    public boolean isTokenInvalid(String token) {
        return invalidatedTokens.contains(token);
    }

    // Nettoyer les tokens expirés de la liste des tokens invalidés
    public void cleanupInvalidatedTokens() {
        Date now = new Date();
        invalidatedTokens.removeIf(token -> {
            try {
                Date expiration = Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getExpiration();
                return expiration.before(now);
            } catch (Exception e) {
            }
        });
    }
}
*/


package com.recrutementTuteur.security.jwt;

import com.recrutementTuteur.data.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class JwtService {
    private String jwtSecret = "YourDefaultVeryLongAndComplexSecretKeyThatShouldBeReplacedInProduction";
    private long jwtExpiration = 86400000; // 24 hours

    private final Set<String> invalidatedTokens = ConcurrentHashMap.newKeySet();

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

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("nom", user.getNom());
        claims.put("prenom", user.getPrenom());
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    public boolean isTokenInvalid(String token) {
        return invalidatedTokens.contains(token);
    }

    public void cleanupInvalidatedTokens() {
        Date now = new Date();
        invalidatedTokens.removeIf(token -> {
            try {
                Date expiration = Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getExpiration();
                return expiration.before(now);
            } catch (Exception e) {
                return true; // Si on ne peut pas parser le token, on le considère comme expiré
            }
        });
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false; // Le token n'est pas valide
        }
    }


    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}