package com.labcenter.security;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.labcenter.auth.Role;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtUtil {

    private static final String SECRET_KEY = "yourSecretKey";
    private static final long EXPIRATION_TIME = 86400000;

    public String generateToken(String username, List<Role> roles) {
        System.out.println(roles.stream().map(Enum::name).collect(Collectors.toList())+ "<<<<<");
        return JWT.create()
                .withSubject(username)
                .withIssuer("project")
                .withClaim("roles", roles.stream().map(Enum::name).collect(Collectors.toList()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (JWTVerificationException e) {
            // Handle the exception or log the error
            throw new RuntimeException("Token verification failed: " + e.getMessage());
        }
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        return decodedJWT.getSubject();
    }

    public List<String> extractRoles(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        List<String> ls = decodedJWT.getClaim("roles").asList(String.class);
        
        return ls;
    }
}
