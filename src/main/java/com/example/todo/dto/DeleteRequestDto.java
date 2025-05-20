package com.example.todo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class DeleteRequestDto {
    private List<Long> ids;
}
