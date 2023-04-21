package com.api.devmed.services;

import com.api.devmed.model.entities.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    public String gerarToken(Usuario usuario){
        return JWT.create()
                .withIssuer("api.DevMed")
                .withSubject(usuario.getUsername())
                .withClaim("id", usuario.getId())
                .withExpiresAt(
                         LocalDateTime.now()
                        .plusHours(3)
                        .toInstant(ZoneOffset.of("-03:00")))
                .sign(Algorithm.HMAC256("Clinica-DevMed-API"));
    }
    public String getSubject(String token){
        return JWT.require(Algorithm.HMAC256("Clinica-DevMed-API"))
                .withIssuer("api.DevMed")
                .build().verify(token).getSubject();
    }
}
