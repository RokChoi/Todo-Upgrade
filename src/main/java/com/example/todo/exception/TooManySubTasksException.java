package com.example.todo.exception;

import com.example.todo.global.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class TooManySubTasksException extends ApplicationException {

    public TooManySubTasksException() {
        super(HttpStatus.BAD_REQUEST.value(), "세부 할 일(SubTask)은 최대 3개까지만 등록할 수 있습니다.");
    }
}
