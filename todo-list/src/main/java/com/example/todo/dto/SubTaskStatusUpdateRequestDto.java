package com.example.todo.dto;

import com.example.todo.domain.entity.SubTaskStatus;
import lombok.Getter;

@Getter
public class SubTaskStatusUpdateRequestDto {
    private SubTaskStatus status;
}
