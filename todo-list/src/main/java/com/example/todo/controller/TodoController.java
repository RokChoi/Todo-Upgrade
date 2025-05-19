package com.example.todo.controller;

import com.example.todo.domain.entity.Todo;
import com.example.todo.domain.entity.TodoStatus;
import com.example.todo.dto.*;
import com.example.todo.service.TodoService;
import com.example.todo.exception.TooManySubTasksException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.example.todo.domain.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<String> createTodo(@RequestBody TodoRequestDto requestDto,
                                             @AuthenticationPrincipal User user) {
        System.out.println(">>> 로그인 사용자: " + user);  // 로그 출력
        if (user == null) {
            return ResponseEntity.status(403).body("인증된 사용자만 접근 가능합니다.");
        }

        if (requestDto.getSubTasks() != null && requestDto.getSubTasks().size() > 3) {
            throw new TooManySubTasksException();
        }

        todoService.createTodo(requestDto, user); // User를 넘겨줄 수 있도록 서비스 메서드도 수정 필요
        return ResponseEntity.ok("TODO 등록 성공");
    }

    @GetMapping
    public List<TodoResponseDto> getAllTodos() {
        return todoService.getAllTodos()
                .stream()
                .map(todo -> new TodoResponseDto(
                        todo.getId(),
                        todo.getTitle(),
                        todo.getStatus(),
                        todo.getSubTasks().stream()
                                .map(SubTaskResponseDto::new)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/page")
    public Page<TodoResponseDto> getAllTodosPage(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        return todoService.getAllTodos(pageable)
                .map(todo -> new TodoResponseDto(
                        todo.getId(),
                        todo.getTitle(),
                        todo.getStatus(),
                        todo.getSubTasks().stream()
                                .map(SubTaskResponseDto::new)
                                .collect(Collectors.toList())
                ));
    }

    @GetMapping("/api/todos")
    public Page<TodoResponseDto> getAllTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return todoService.getAllTodos(pageable)
                .map(todo -> {
                    List<SubTaskResponseDto> subTaskDtos = todo.getSubTasks().stream()
                            .map(sub -> new SubTaskResponseDto(sub))
                            .collect(Collectors.toList());

                    return new TodoResponseDto(
                            todo.getId(),
                            todo.getTitle(),
                            todo.getStatus(),
                            subTaskDtos
                    );
                });
    }

    @PatchMapping("/api/todos/{id}/status")
    public ResponseEntity<String> updateTodoStatus(@PathVariable Long id,
                                                   @RequestBody TodoStatusUpdateRequestDto requestDto) {
        todoService.updateTodoStatus(id, requestDto.getStatus());
        return ResponseEntity.ok("TODO 상태 변경 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok("TODO 삭제 완료");
    }

    @DeleteMapping("/batch")
    public ResponseEntity<List<Long>> deleteMultipleTodos(@RequestBody DeleteRequestDto requestDto) {
        List<Long> deletedIds = todoService.deleteMultipleTodos(requestDto.getIds());
        return ResponseEntity.ok(deletedIds);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMultipleTodos(@RequestBody List<Long> todoIds) {
        List<Long> deletedIds = todoService.deleteMultipleTodos(todoIds);
        return ResponseEntity.ok("삭제된 TODO ID: " + deletedIds);
    }

}
