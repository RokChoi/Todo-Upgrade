package com.example.todo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoRequestDto {
    private String title;
    private List<SubTaskRequestDto> subTasks;
}
