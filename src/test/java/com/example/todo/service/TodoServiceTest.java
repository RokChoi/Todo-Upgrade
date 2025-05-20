package com.example.todo.service;

import com.example.todo.domain.entity.Todo;
import com.example.todo.domain.repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @DisplayName("Todo 여러 개 삭제 성공 테스트")
    void deleteMultipleTodos_success() {
        // given
        Todo todo1 = todoRepository.save(new Todo("Todo1"));
        Todo todo2 = todoRepository.save(new Todo("Todo2"));
        List<Long> ids = List.of(todo1.getId(), todo2.getId());

        // when
        todoService.deleteMultipleTodos(ids);

        // then
        assertTrue(todoRepository.findById(todo1.getId()).isEmpty());
        assertTrue(todoRepository.findById(todo2.getId()).isEmpty());
    }
}
