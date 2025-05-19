package com.example.todo.controller;

import com.example.todo.domain.entity.SubTask;
import com.example.todo.domain.entity.Todo;
import com.example.todo.dto.DeleteRequestDto;
import com.example.todo.dto.SubTaskRequestDto;
import com.example.todo.dto.SubTaskResponseDto;
import com.example.todo.dto.SubTaskStatusUpdateRequestDto;
import com.example.todo.service.SubTaskService;
import com.example.todo.domain.entity.SubTaskStatus;
import com.example.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subtasks")
@RequiredArgsConstructor
public class SubTaskController {

    private final SubTaskService subTaskService;
    private final TodoService todoService;

    @PostMapping
    public SubTaskResponseDto createSubTask(@RequestBody SubTaskRequestDto requestDto) {
        Todo todo = todoService.findById(requestDto.getTodoId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 TODO가 없습니다"));

        SubTask subTask = new SubTask(requestDto.getTitle(), todo);
        SubTask savedSubTask = subTaskService.createSubTask(subTask);

        return new SubTaskResponseDto(
                savedSubTask.getId(),
                savedSubTask.getTitle(),
                savedSubTask.getStatus(),
                savedSubTask.getTodo().getId()
        );
    }

    @GetMapping("/{id}")
    public SubTaskResponseDto getSubTaskById(@PathVariable Long id) {
        SubTask subTask = subTaskService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 SubTask가 없습니다: " + id));
        return new SubTaskResponseDto(subTask.getId(), subTask.getTitle(), subTask.getStatus(), subTask.getTodo().getId());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateSubTaskStatus(@PathVariable Long id,
                                                      @RequestBody SubTaskStatusUpdateRequestDto requestDto) {
        subTaskService.updateSubTaskStatus(id, requestDto.getStatus());
        return ResponseEntity.ok("SubTask 상태 변경 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubTask(@PathVariable Long id) {
        subTaskService.deleteSubTask(id);
        return ResponseEntity.ok("SubTask 삭제 완료");
    }

    @DeleteMapping("/batch")
    public ResponseEntity<List<Long>> deleteMultipleSubTasks(@RequestBody DeleteRequestDto requestDto) {
        List<Long> deletedIds = subTaskService.deleteMultipleSubTasks(requestDto.getIds());
        return ResponseEntity.ok(deletedIds);
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<String> deleteMultipleSubTasks(@RequestBody List<Long> ids) {
        subTaskService.deleteMultipleSubTasks(ids);
        return ResponseEntity.ok("여러 개 SubTask 삭제 성공");
    }

}
