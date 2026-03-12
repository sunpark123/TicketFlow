package com.sunpark.ticketflow.gateway.JWT;


import com.sunpark.ticketflow.gateway.Enum.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret.access}")
    private String ACCESS_SECRET;


    private Key getAccessKey() {
        return Keys.hmacShaKeyFor(ACCESS_SECRET.getBytes());
    }



    public boolean validateAccessToken(String token) {
        try {
            getAccessClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public String extractAccessUserId(String token) {
        return getAccessClaims(token).getSubject();
    }

    public UserRole extractAccessRole(String token) {
        return UserRole.valueOf(getAccessClaims(token).get("role", String.class));
    }

    private Claims getAccessClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getAccessKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
