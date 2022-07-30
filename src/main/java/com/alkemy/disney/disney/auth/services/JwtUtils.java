package com.alkemy.disney.disney.auth.services;

import com.alkemy.disney.disney.auth.entity.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class JwtUtils {

    public static String createAccessToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String accessToken = JWT.create().withSubject(userDetails.getUsername()).withExpiresAt(toMinutes(15)).sign(algorithm);
        return accessToken;
    }

    public static String createAccessToken(UserEntity userEntity) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String accessToken = JWT.create().withSubject(userEntity.getUsername()).withExpiresAt(toMinutes(15)).sign(algorithm);
        return accessToken;
    }

    public static String createRefreshToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String refreshToken = JWT.create().withSubject(userDetails.getUsername()).withExpiresAt(toMinutes(60)).sign(algorithm);
        return refreshToken;
    }

    public static String decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    private static Date toMinutes(Integer minutes) {
        return new Date(System.currentTimeMillis() + 1000 * 60 * minutes);
    }

}
