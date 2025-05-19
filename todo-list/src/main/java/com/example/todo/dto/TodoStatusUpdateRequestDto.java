package com.example.todo.dto;

import com.example.todo.domain.entity.TodoStatus;
import lombok.Getter;

@Getter
public class TodoStatusUpdateRequestDto {
    private TodoStatus status;
}

