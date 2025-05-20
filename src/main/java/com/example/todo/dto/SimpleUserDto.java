package com.example.todo.dto;

import com.example.todo.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleUserDto {
    private Long id;
    private String username;

    public static SimpleUserDto fromEntity(User user) {
        return new SimpleUserDto(user.getId(), user.getUsername());
    }
}
