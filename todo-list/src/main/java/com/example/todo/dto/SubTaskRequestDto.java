package com.example.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubTaskRequestDto {
    private String title;
    private Long todoId;
}

