package com.example.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponseDto {

    private Long id;
    private String title;
    private String status;
    private int viewCount;

    private List<SubTaskResponseDto> subTasks;

    public static TodoResponseDto fromEntity(TodoList todo, List<SubTask> subTasks) {
        return TodoResponseDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .status(todo.getStatus().name())
                .viewCount(todo.getViewCount())
                .subTasks(
                        subTasks.stream()
                                .map(SubTaskResponseDto::fromEntity)
                                .toList()
                )
                .build();
    }
}
