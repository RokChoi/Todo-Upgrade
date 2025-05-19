package com.example.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String email;

    public UserResponseDto(Object id, Object email) {
    }
}
