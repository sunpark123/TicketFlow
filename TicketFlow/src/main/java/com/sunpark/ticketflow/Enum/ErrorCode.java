package com.sunpark.ticketflow.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_VERIFICATION(HttpStatus.CONFLICT, "번호 인증이 안 된 사용자에요."),
    EXIST_PHONE(HttpStatus.CONFLICT, "이미 인증이 된 번호에요."),
    INCORRECT_CODE(HttpStatus.NOT_FOUND, "코드가 다릅니다."),
    RETRY_VERIFICATION(HttpStatus.BAD_REQUEST, "번호 인증을 다시 해주세요."),
    ALREADY_VERIFICATION(HttpStatus.ALREADY_REPORTED, "번호 인증을 이미 했습니다."),
    ALREADY_USED_USERID_VERIFICATION(HttpStatus.ALREADY_REPORTED, "존재하는 아이디입니다."),


    EXIST_USERID(HttpStatus.CONFLICT, "이미 사용 중인 아이디에요"),
    INVALID_VERIFY_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 토큰이에요"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저예요"),

    INCORRECT_LOGIN(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 다릅니다."),

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),

    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    
    NOT_FOUND_EVENT(HttpStatus.NOT_FOUND, "이벤트가 없습니다.");

    private final HttpStatus status;
    private final String message;
}