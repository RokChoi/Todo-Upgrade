package com.example.todo.domain.repository;

import com.example.todo.domain.entity.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
}

