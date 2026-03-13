package com.sunpark.ticketflow.Controller;

import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.Enum.UserRole;
import com.sunpark.ticketflow.ExceptionHandling.CustomException;
import com.sunpark.ticketflow.JWT.JwtUtil;
import com.sunpark.ticketflow.Service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/get/access")
    public ResponseEntity<Map<String, String>> getAccessToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        Map<String, String> userInfoMap = tokenService.validRefreshToken(refreshToken);

        Map<String, String> tokenMap = tokenService.getNewToken(
                userInfoMap.get("userId"),
                UserRole.valueOf(userInfoMap.get("userRole"))
        );

        String newAccessToken = tokenMap.get("accessToken");
        String newRefreshToken = tokenMap.get("refreshToken");


        ResponseCookie cookie = ResponseCookie.from("refreshToken", newRefreshToken)
                .maxAge(3600)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("accessToken", newAccessToken));
    }
}
