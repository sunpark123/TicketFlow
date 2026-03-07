package com.sunpark.ticketflow.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 아이디에요"),
    INVALID_VERIFY_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 토큰이에요"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저예요");

    private final HttpStatus status;
    private final String message;
}