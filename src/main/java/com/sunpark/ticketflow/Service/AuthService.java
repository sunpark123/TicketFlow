package com.sunpark.ticketflow.Service;

import com.sunpark.ticketflow.CustomException;
import com.sunpark.ticketflow.DTO.AuthDTO;
import com.sunpark.ticketflow.Entity.UserEntity;
import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.Enum.UserRole;
import com.sunpark.ticketflow.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(AuthDTO authDTO) {
        if(userRepository.existsByUserId(authDTO.getUserId())){
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(authDTO.getPassword());
        UserEntity userEntity = UserEntity.builder()
                .userId(authDTO.getUserId())
                .password(encodedPassword)
                .name(authDTO.getName())
                .phone(authDTO.getPhone())
                .role(UserRole.user)

                .build();

        userRepository.save(userEntity);
    }
}
