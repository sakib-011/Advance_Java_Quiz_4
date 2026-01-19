package com.sakib.quiz_4.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    private final byte[] SECRET_KEY = "dfakldsjfk_ladjflkadjk_lfajdslkfajd_lkfjalksdfj".getBytes(StandardCharsets.UTF_8);
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY);

    public String generateJwtToken(String subject , List<String> role){

        Map<String , Object> claims = new HashMap<>();
        claims.put("roles" , role);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .signWith(secretKey)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 900000))
                .compact();

    }

    public Claims parseJwtToken(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUsername(String token){
        Claims claims = parseJwtToken(token);
        return claims.getSubject();
    }

    public List<String> getRoles(String token){
        Claims claims = parseJwtToken(token);
        return claims.get("roles" , List.class);
    }

}
