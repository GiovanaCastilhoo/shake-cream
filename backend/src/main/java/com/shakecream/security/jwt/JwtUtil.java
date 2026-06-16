package com.shakecream.security.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
    private static final String SECRET = "shakecream_super_secret_key_2026_very_long";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; 
    
    
    public static String generateToken(String email, String role) {
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET));
                
    }
    
    public static String generateCustomerToken(String name, int tableNumber) {
    return JWT.create()
            .withSubject(name)
            .withClaim("table", tableNumber)
            .withClaim("type", "customer")
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(Algorithm.HMAC256(SECRET));
    }

    // Validar token (retorna o JWT decodificado)
    public static DecodedJWT validateToken(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);
    }

    // Pegar email do token
    public static String getEmail(String token) {
        return validateToken(token).getSubject();
    }

    // Pegar role (admin/user)
    public static String getRole(String token) {
        return validateToken(token).getClaim("role").asString();
    }

    public static int getTable(String token) {
        return validateToken(token).getClaim("table").asInt();
    }
}