package com.example.todo.exception;

import com.example.todo.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class SubTaskNotFoundException extends ApplicationException {
    public SubTaskNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "해당 SubTask가 존재하지 않습니다.");
    }
}
