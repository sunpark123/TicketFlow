package com.sunpark.ticketflow.AOP;

import com.sunpark.ticketflow.Enum.ErrorCode;
import com.sunpark.ticketflow.Enum.UserRole;
import com.sunpark.ticketflow.ExceptionHandling.CustomException;
import com.sunpark.ticketflow.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthAspect {

    private final HttpServletRequest request;
    private final UserService userService;

    @Before("@annotation(AdminOnly)")
    public void checkAdmin() {
        String userId = request.getHeader("X-User-Id");
        String userRole = request.getHeader("X-User-Role");

        if (userId == null || userRole == null) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        if (!UserRole.valueOf(userRole).equals(UserRole.ADMIN)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        if (!userService.isAdmin(userId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
    }
}