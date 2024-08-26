package com.example.e_commerce.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    public String generateToken(String username) {
       return Jwts.builder().setId(username)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode("hello".getBytes()))
                .compact();

    }
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encode("hello".getBytes()))
                .parseClaimsJws(token).getBody();
    }

    public String getUsername(String token) {
        return parseToken(token).getId();
    }
    public Date getExpiration(String token) {
        return parseToken(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        Date issuedAt = parseToken(token).getIssuedAt();
        Date expiration = getExpiration(token);
        return expiration.before(issuedAt);
    }
    public boolean validateToken(String token,String userName) {
        return (getUsername(token).equals(userName)&& !isTokenExpired(token));
    }
}
