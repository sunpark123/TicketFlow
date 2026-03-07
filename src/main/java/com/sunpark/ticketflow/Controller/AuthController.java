package com.sunpark.ticketflow.Controller;

import com.sunpark.ticketflow.DTO.AuthDTO;
import com.sunpark.ticketflow.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid AuthDTO authDTO) {
        authService.registerUser(authDTO);
        return ResponseEntity.ok().build();
    }
}
