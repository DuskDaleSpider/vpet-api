package com.dakota.vpet.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dakota.vpet.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JWTUtil {
    
    @SuppressWarnings("deprecation")
    public static String generateToken(User user) throws JsonProcessingException{
        String secret = System.getenv("JWT_SECRET") != null ? System.getenv("JWT_SECRET") : "ThisIsASuperSecretJWTSecret";
        Algorithm alg = Algorithm.HMAC256(secret)   ;
        Date now = new Date();
        Date twoHours = new Date(now.getYear(), now.getMonth(), now.getDate(), now.getHours() + 2, now.getMinutes());
        Map<String, String> payload = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        payload.put("user", mapper.writeValueAsString(user));
        String token = JWT.create().withIssuer("vpet").withPayload(payload).withExpiresAt(twoHours).sign(alg);
        return token;
    }


}
