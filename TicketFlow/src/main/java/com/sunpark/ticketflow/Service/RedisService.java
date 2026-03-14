package com.sunpark.ticketflow.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public void saveRefreshToken(String userId, String refreshToken) {
        redisTemplate.opsForValue().set(
                "refreshToken:" + userId,
                refreshToken,
                Duration.ofDays(7)
        );
    }

    public boolean reservationTry(Integer eventId, Integer seatNumber, String userId) {
        Boolean result = redisTemplate.opsForValue()
                .setIfAbsent("reservation:" + eventId + ":" + seatNumber, userId, 300, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(result);
    }

    public void deleteReservation(Integer eventId, Integer seatNumber) {
        redisTemplate.delete(
                "reservation:" + eventId + ":" + seatNumber);

    }
}
