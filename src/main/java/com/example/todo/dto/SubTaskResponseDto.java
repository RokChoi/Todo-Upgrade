package com.example.todo.dto;

import com.example.todo.domain.entity.SubTask;
import com.example.todo.domain.entity.SubTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubTaskResponseDto {

    private Long id;
    private String title;
    private String status;

    public static SubTaskResponseDto fromEntity(SubTask subTask) {
        return SubTaskResponseDto.builder()
                .id(subTask.getId())
                .title(subTask.getTitle())
                .status(subTask.getStatus().name())
                .build();
    }
}


