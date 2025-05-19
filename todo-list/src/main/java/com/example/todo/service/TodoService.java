package com.example.todo.service;

import com.example.todo.domain.entity.SubTask;
import com.example.todo.domain.entity.Todo;
import com.example.todo.domain.entity.TodoStatus;
import com.example.todo.domain.repository.SubTaskRepository;
import com.example.todo.domain.repository.TodoRepository;
import com.example.todo.dto.SubTaskRequestDto;
import com.example.todo.dto.TodoRequestDto;
import com.example.todo.exception.TooManySubTasksException;
import com.example.todo.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final SubTaskRepository subTaskRepository;

    public Todo createTodo(TodoRequestDto requestDto, User user) {
        if (requestDto.getSubTasks().size() > 3) {
            throw new TooManySubTasksException();  // 커스텀 예외
        }

        Todo todo = new Todo(requestDto.getTitle());
        Todo savedTodo = todoRepository.save(todo);

        for (SubTaskRequestDto subTaskDto : requestDto.getSubTasks()) {
            SubTask subTask = new SubTask(subTaskDto.getTitle(), savedTodo);
            subTaskRepository.save(subTask);
        }

        return savedTodo;
    }

    public Page<Todo> getAllTodos(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public void updateTodoStatus(Long id, TodoStatus status) {
        Todo todo = findById(id)
                .orElseThrow(UserNotFoundException::new);
        todo.setStatus(status);
        todoRepository.save(todo);
    }

    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    public void deleteTodo(Long id) {
        Todo todo = findById(id)
                .orElseThrow(UserNotFoundException::new);
        todoRepository.delete(todo);
    }

    public List<Long> deleteMultipleTodos(List<Long> ids) {
        List<Long> deleted = new ArrayList<>();
        for (Long id : ids) {
            Todo todo = findById(id)
                    .orElseThrow(UserNotFoundException::new);
            todoRepository.delete(todo);
            deleted.add(id);
        }
        return deleted;
    }


}
