package com.example.todo.domain.repository;

import com.example.todo.domain.entity.TodoList;
import com.example.todo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {

    List<TodoList> findByOwner(User user);

    @Modifying
    @Transactional
    @Query("UPDATE TodoList t SET t.viewCount = t.viewCount + 1 WHERE t.id = :todoId")
    void incrementViewCount(Long todoId);
}
