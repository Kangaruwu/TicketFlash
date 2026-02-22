package com.barox.ticketflash.security;

import com.barox.ticketflash.dto.response.TokenResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.LocalDateTime;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;

    public TokenResponse generateToken(Authentication authentication) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime accessTokenExpiration = now.plusDays(jwtExpiration);
        LocalDateTime refreshTokenExpiration = now.plusDays(jwtExpiration).plusDays(7);

        String role = authentication.getAuthorities().stream()
            .findFirst()
            .map(auth -> auth.getAuthority())
            .orElse("");

        String accessToken = Jwts.builder()
            .subject(authentication.getName())
            .claim("token_type", "ACCESS")
            .claim("role", role)
            .issuedAt(java.sql.Timestamp.valueOf(now))
            .expiration(java.sql.Timestamp.valueOf(accessTokenExpiration))
            .signWith(key())
            .compact();

        String refreshToken = Jwts.builder()
            .subject(authentication.getName())
            .claim("token_type", "REFRESH")
            .issuedAt(java.sql.Timestamp.valueOf(now))
            .expiration(java.sql.Timestamp.valueOf(refreshTokenExpiration))
            .signWith(key())
            .compact();

        return new TokenResponse(accessToken, refreshToken);
    }

    public boolean validateToken(String token) throws Exception {
        try {
            Jwts.parser().verifyWith(key()).build().parse(token);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .verifyWith(key())
            .build()
            .parseSignedClaims(token) 
            .getPayload()            
            .getSubject();
    }
}
