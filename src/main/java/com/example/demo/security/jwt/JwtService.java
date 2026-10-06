package com.example.demo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class JwtService {
    private final SecretKey SECRET_KEY = Jwts.SIG.HS256.key()
                                                       .build();
    private final long JWT_EXPIRATION = 1000 * 60 * 15;

    //PAYLOAD EXTRACTION
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                   .verifyWith(SECRET_KEY)
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //TOKEN GENERATION
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<String, Object>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                   .claims(extraClaims)
                   .subject(userDetails.getUsername())
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                   .signWith(SECRET_KEY)
                   .compact();
    }

    //TOKEN VALIDATION
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
