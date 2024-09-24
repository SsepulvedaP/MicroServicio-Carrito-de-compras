package com.microservice.microserviceshoppingcart.infrastructure.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.microservice.microserviceshoppingcart.utils.Constants.*;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }



    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public List<String> extractRole(String token) {
        return extractClaim(token, claims -> {
            Object rolesObj = claims.get(ROLE);
            List<String> roleNames = new ArrayList<>();
            if (rolesObj instanceof List<?> roles) {
                for (Object roleObj : roles) {
                    if (roleObj instanceof Map<?, ?> role) {
                        Object authority = role.get(AUTHORITY);
                        if (authority instanceof String) {
                            roleNames.add((String) authority);
                        }
                    }
                }
            }
            return roleNames;
        });
    }

    public Long extractId(String token) {
        return extractClaim(token, claims -> claims.get(ID, Long.class));
    }
}
