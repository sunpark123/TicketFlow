package com.sunpark.ticketflow.Service;


import com.sunpark.ticketflow.Enum.UserRole;
import com.sunpark.ticketflow.JWT.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;


    public Map<String, String> getNewToken(String userId, UserRole userRole) {
        String accessToken = jwtUtil.generateAccessToken(userId, userRole);
        String refreshToken = jwtUtil.generateRefreshToken(userId, userRole);

        redisTemplate.opsForValue().set(
                "refreshToken:" + userId,
                refreshToken,
                Duration.ofDays(7)
        );
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        return tokenMap;
    }
}
