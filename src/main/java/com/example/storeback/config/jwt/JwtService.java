package com.example.storeback.config.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.storeback.config.CustomUserDetails;
import com.example.storeback.config.jwt.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;



    public String generateToken(CustomUserDetails userDetails){
        var authorities= userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return JWT.create()
                .withSubject(userDetails.getUserId().toString())
                .withExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                .withClaim("email",userDetails.getEmail())
                .withClaim("authorities",authorities)
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}
