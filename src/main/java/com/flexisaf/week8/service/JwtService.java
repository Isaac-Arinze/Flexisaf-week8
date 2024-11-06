package com.flexisaf.week8.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
//    private static final String SECRET_KEY = "8e3f0b1d5d2a4c8b92e1fc1a765bf2434d6c72e6f5b943e2c1c1e2d6b4db7a22";


    private String secretKey = "8e3f0b1d5d2a4c8b92e1fc1a765bf2434d6c72e6f5b943e2c1c1e2d6b4db7a22";;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        // Add any claims you need in the token, e.g., roles or user-related data
        claims.put("sub", userDetails.getUsername()); // example claim

        // You can use your existing logic to create the token here
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())  // use the username or email
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // 1 hour expiration
                .signWith(SignatureAlgorithm.HS256, secretKey)  // ensure you have a secretKey configured
                .compact();
    }

    private SecretKey generateKey() {

        byte[] decode = Decoders.BASE64.decode(getSecretKey());

        return Keys.hmacShaKeyFor(decode);
    }

    public String getSecretKey() {
        return secretKey = "d5d2a4c8b92e1fc1a765bf2434d6c72e6f5b943e2c1c1e2d6b4db7a21";
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);

    }
    private Claims extractClaims(String token){
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(tokengit
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
