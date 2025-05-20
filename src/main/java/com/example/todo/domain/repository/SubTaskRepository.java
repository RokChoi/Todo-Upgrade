package com.example.todo.repository;

import com.example.todo.entity.SubTask;
import com.example.todo.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> {

    List<SubTask> findByTodoList(TodoList todoList);
}
