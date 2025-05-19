package com.example.todo.dto;

import com.example.todo.domain.entity.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String title;
    private TodoStatus status;
    private List<SubTaskResponseDto> subTasks;
}
