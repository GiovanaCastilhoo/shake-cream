package com.shakecream.util;

import spark.Request;
import com.shakecream.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthUtil {

    private static final String SECRET = "shakecream_super_secret_key_2026_very_long";

    public static User getUserFromToken(Request req) {

        String authHeader = req.headers("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("TOKEN_NOT_PROVIDED");
        }

        String token = authHeader.replace("Bearer ", "");

        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET))
                .build()
                .verify(token);

        User user = new User();
        user.setRole(jwt.getClaim("role").asString());

        return user;
    }

    public static void validateAdmin(User user) {
        if (user == null || user.getRole() == null || !user.getRole().equals("ADMIN")) {
            throw new RuntimeException("UNAUTHORIZED");
        }
    }
}