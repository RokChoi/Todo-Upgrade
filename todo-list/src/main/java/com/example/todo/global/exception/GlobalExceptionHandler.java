// GlobalExceptionHandler.java
package com.example.todo.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessException(ApplicationException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponseDto(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponseDto(400, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleEtc(Exception e) {
        return ResponseEntity
                .status(500)
                .body(new ErrorResponseDto(500, "알 수 없는 서버 에러 발생"));
    }
}



