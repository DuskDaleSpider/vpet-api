package com.dakota.vpet.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dakota.vpet.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JWTUtil {
    
    @SuppressWarnings("deprecation")
    public static String generateToken(User user) throws JsonProcessingException{
        Date now = new Date();
        Date twoHours = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours() + 2, now.getMinutes());
        Map<String, String> payload = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        payload.put("user", mapper.writeValueAsString(user));
        String token = JWT.create().withIssuer("vpet").withPayload(payload).withExpiresAt(twoHours).sign(getAlgorithm());
        return token;
    }

    public static User verifyToken(String token) throws JsonMappingException, JsonProcessingException{
        JWTVerifier verifier = JWT.require(getAlgorithm()).withIssuer("vpet").build();
        DecodedJWT jwt = verifier.verify(token);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jwt.getClaim("user").asString(), User.class);
    }

    private static Algorithm getAlgorithm(){
        String secret = System.getenv("JWT_SECRET") != null ? System.getenv("JWT_SECRET") : "ThisIsASuperSecretJWTSecret";
        return Algorithm.HMAC256(secret);
    }
}
