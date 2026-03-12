package com.sunpark.ticketflow.JWT;


import com.sunpark.ticketflow.Enum.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${jwt.secret.access}")
    private String ACCESS_SECRET;
    @Value("${jwt.secret.refresh}")
    private String REFRESH_SECRET;

    private Key getAccessKey() {
        return Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes());
    }
    private Key getRefreshKey() {
        return Keys.hmacShaKeyFor(REFRESH_SECRET.getBytes());
    }

    public String generateAccessToken(String userId, UserRole userRole) {
        //30분
        long ACCESS_TOKEN_TIME = 1000 * 60 * 30;

        return Jwts.builder()
                .setSubject(userId)
                .claim("type", "access")
                .claim("role", userRole)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_TIME))
                .signWith(getAccessKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String userId, UserRole userRole) {
        //7일
        long REFRESH_TOKEN_TIME = 1000 * 60 * 60 * 24 * 7;

        return Jwts.builder()
                .setSubject(userId)
                .setId(UUID.randomUUID().toString())
                .claim("type", "refresh")
                .claim("role", userRole)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_TIME))
                .signWith(getRefreshKey(), SignatureAlgorithm.HS256)
                .compact();
    }




    public boolean validateRefreshToken(String token) {
        try {
            getRefreshClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public String extractRefreshUserId(String token) {
        return getRefreshClaims(token).getSubject();
    }
    public UserRole extractRefreshRole(String token) {
        return UserRole.valueOf(getRefreshClaims(token).get("role", String.class));
    }

    private Claims getRefreshClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getRefreshKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
