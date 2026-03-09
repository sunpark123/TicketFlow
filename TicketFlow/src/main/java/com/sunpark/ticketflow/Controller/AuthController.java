package com.sunpark.ticketflow.Controller;

import com.sunpark.ticketflow.DTO.AuthDTO;
import com.sunpark.ticketflow.DTO.LoginDTO;
import com.sunpark.ticketflow.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid AuthDTO authDTO) {
        authService.registerUser(authDTO);
        return new ResponseEntity<>("Success Register", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody @Valid LoginDTO loginDTO) {
        Map<String, String> tokenMap = authService.loginUser(loginDTO);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", tokenMap.get("refreshToken"))
                .maxAge(3600)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("accessToken", tokenMap.get("accessToken")));
    }
}
