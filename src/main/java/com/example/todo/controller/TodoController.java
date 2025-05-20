package com.example.todo.controller;

import com.example.todo.entity.User;
import com.example.todo.service.TodoService;
import com.example.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/my")
    public List<TodoResponseDto> getMyTodos(@RequestParam Long userId) {
        return todoService.getMyTodoList(userId);
    }

    // 다른 사람의 todo 리스트 조회 (조회수 증가 포함)
    @GetMapping("/shared")
    public List<Map<String, Object>> getSharedTodoList(
            @RequestParam Long viewerId,
            @RequestParam Long targetUserId) {

        return todoService.getTodoListWithSubtasks(viewerId, targetUserId);
    }

    // 나의 팔로우 목록 조회
    @GetMapping("/followings")
    public List<Map<String, Object>> getMyFollowings(@RequestParam Long userId) {
        return todoService.getFollowingList(userId);
    }

    @GetMapping("/shared-todo")
    public ResponseEntity<List<Map<String, Object>>> getSharedTodos(
            @RequestParam Long viewerId,
            @RequestParam Long targetUserId
    ) {
        List<Map<String, Object>> result = todoService.getTodoListWithSubtasks(viewerId, targetUserId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/followings")
    public ResponseEntity<Set<SimpleUserDto>> getFollowings(
            @RequestHeader("userId") Long userId) {

        Set<User> followings = todoService.getFollowings(userId);

        Set<SimpleUserDto> result = followings.stream().map(SimpleUserDto::fromEntity).collect(Collectors.toSet());

        return ResponseEntity.ok(result);
    }

    // 내가 팔로우한 사람들의 목록 조회
    @GetMapping("/followings/{userId}")
    public ResponseEntity<Set<User>> getFollowings(@PathVariable Long userId) {
        Set<User> followings = todoService.getFollowings(userId);
        return ResponseEntity.ok(followings);
    }

    // 특정 팔로우 대상자의 투두 리스트 + 서브태스크 + 조회수 포함
    @GetMapping("/shared/{viewerId}/{targetUserId}")
    public ResponseEntity<List<Map<String, Object>>> getSharedTodoList(
            @PathVariable Long viewerId,
            @PathVariable Long targetUserId
    ) {
        List<Map<String, Object>> todoWithSubs = todoService.getTodoListWithSubtasks(viewerId, targetUserId);
        return ResponseEntity.ok(todoWithSubs);
    }

    @GetMapping("/api/todo/user/{targetUserId}")
    public ResponseEntity<List<TodoResponseDto>> getTodoListByUser(
            @RequestHeader("userId") Long viewerId,
            @PathVariable Long targetUserId) {

        List<Map<String, Object>> todos = todoService.getTodoListWithSubtasks(viewerId, targetUserId);

        List<TodoResponseDto> response = todos.stream().map(todoMap -> {
            TodoList todo = (TodoList) todoMap.get("todo");
            List<SubTask> subTasks = (List<SubTask>) todoMap.get("subTasks");

            return TodoResponseDto.fromEntity(todo, subTasks);
        }).toList();

        return ResponseEntity.ok(response);
    }
}



