package com.example.todo.dto;

import com.example.todo.entity.SubTask;
import com.example.todo.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class TodoWithSubTaskResponseDto {
    private Long todoId;
    private String title;
    private String status;
    private Integer viewCount;
    private List<SubTaskDto> subTasks;

    public static TodoWithSubTaskResponseDto from(Todo todo) {
        return new TodoWithSubTaskResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getStatus().name(),
                todo.getViewCount(),
                todo.getSubTasks().stream()
                        .map(SubTaskDto::from)
                        .collect(Collectors.toList())
        );
    }

    @Getter
    @AllArgsConstructor
    public static class SubTaskDto {
        private Long id;
        private String content;
        private String status;

        public static SubTaskDto from(SubTask subTask) {
            return new SubTaskDto(
                    subTask.getId(),
                    subTask.getContent(),
                    subTask.getStatus().name()
            );
        }
    }
}
