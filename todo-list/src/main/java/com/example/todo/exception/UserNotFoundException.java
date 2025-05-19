package com.example.todo.exception;

import com.example.todo.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "유저를 찾을 수 없습니다.");
    }
}
