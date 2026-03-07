package com.sunpark.ticketflow.ExceptionHandling;

import com.sunpark.ticketflow.Enum.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e){

        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(Map.of(
                        "status", errorCode.getStatus(),
                        "message", errorCode.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e){

        String message = Objects.requireNonNull(e.getBindingResult()
                        .getFieldError())
                .getDefaultMessage();

        assert message != null;
        return ResponseEntity
                .badRequest()
                .body(Map.of(
                        "status", 400,
                        "message", message
                ));
    }

}