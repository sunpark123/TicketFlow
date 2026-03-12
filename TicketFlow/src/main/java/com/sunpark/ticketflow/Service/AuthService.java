package com.sunpark.ticketflow.Service;

import com.sunpark.ticketflow.DTO.LoginDTO;
import com.sunpark.ticketflow.Entity.VerificationEntity;
import com.sunpark.ticketflow.ExceptionHandling.CustomException;
import com.sunpark.ticketflow.DTO.AuthDTO;
import com.sunpark.ticketflow.Entity.UserEntity;
import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.Enum.UserRole;
import com.sunpark.ticketflow.JWT.JwtUtil;
import com.sunpark.ticketflow.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;
    private final TokenService tokenService;


    public void registerUser(AuthDTO authDTO) {
        if(!verificationService.checkVerification(authDTO.getUserId(), authDTO.getPhone())){
            throw new CustomException(ErrorCode.NOT_VERIFICATION);
        }
        if(userRepository.existsByUserId(authDTO.getUserId())){
            throw new CustomException(ErrorCode.EXIST_USERID);
        }

        String encodedPassword = passwordEncoder.encode(authDTO.getPassword());
        UserEntity userEntity = UserEntity.builder()
                .userId(authDTO.getUserId())
                .password(encodedPassword)
                .name(authDTO.getName())
                .phone(authDTO.getPhone())
                .role(UserRole.USER)
                .build();

        userRepository.save(userEntity);
    }

    public Map<String, String> loginUser(LoginDTO loginDTO) {
        String userId = loginDTO.getUserId();

        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null){
            throw new CustomException(ErrorCode.INCORRECT_LOGIN);
        }
        if(!passwordEncoder.matches(loginDTO.getPassword(), userEntity.getPassword())){
            throw new CustomException(ErrorCode.INCORRECT_LOGIN);
        }

        return tokenService.getNewToken(userId, userEntity.getRole());
    }
}
