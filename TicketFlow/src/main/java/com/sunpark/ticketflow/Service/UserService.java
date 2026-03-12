package com.sunpark.ticketflow.Service;

import com.sunpark.ticketflow.Entity.UserEntity;
import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.Enum.UserRole;
import com.sunpark.ticketflow.ExceptionHandling.CustomException;
import com.sunpark.ticketflow.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean isAdmin(String userId){
        boolean isAdminUser = userRepository.existsByUserIdAndRole(userId, UserRole.ADMIN);
        if(!isAdminUser) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        return isAdminUser;
    }
}
