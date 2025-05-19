package com.example.todo.dto;

import com.example.todo.domain.entity.SubTask;
import com.example.todo.domain.entity.SubTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SubTaskResponseDto {
    private Long id;
    private String title;
    private SubTaskStatus status;
    private Long todoId;

    public SubTaskResponseDto(SubTask subTask) {
        this.id = subTask.getId();
        this.title = subTask.getTitle();
        this.status = subTask.getStatus();
        this.todoId = subTask.getTodo() != null ? subTask.getTodo().getId() : null;
    }
}
