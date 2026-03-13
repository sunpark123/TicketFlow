package com.sunpark.ticketflow.Service;


import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.Enum.UserRole;
import com.sunpark.ticketflow.ExceptionHandling.CustomException;
import com.sunpark.ticketflow.JWT.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {
    private final JwtUtil jwtUtil;
    private final RedisService redisService;


    public Map<String, String> validRefreshToken(String refreshToken) {
        boolean tokenValid = jwtUtil.validateRefreshToken(refreshToken);
        if (!tokenValid) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        String userId = jwtUtil.extractRefreshUserId(refreshToken);
        UserRole userRole = jwtUtil.extractRefreshRole(refreshToken);

        Map<String, String> userInfo = new HashMap<>();
        userInfo.put("userId", userId);
        userInfo.put("userRole", userRole.name());

        return userInfo;
    }

    public Map<String, String> getNewToken(String userId, UserRole userRole) {
        String accessToken = jwtUtil.generateAccessToken(userId, userRole);
        String refreshToken = jwtUtil.generateRefreshToken(userId, userRole);

        redisService.saveRefreshToken(userId, refreshToken);

        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        return tokenMap;
    }


}
