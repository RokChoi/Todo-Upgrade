package com.example.todo.service;

import com.example.todo.domain.entity.SubTask;
import com.example.todo.domain.repository.SubTaskRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class SubTaskServiceTest {

    @Autowired
    private SubTaskService subTaskService;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Test
    @DisplayName("SubTask 여러 개 삭제 성공 테스트")
    void deleteMultipleSubTasks_success() {
        // given
        SubTask subTask1 = subTaskRepository.save(new SubTask("Sub1", null));
        SubTask subTask2 = subTaskRepository.save(new SubTask("Sub2", null));
        List<Long> ids = List.of(subTask1.getId(), subTask2.getId());

        // when
        subTaskService.deleteMultipleSubTasks(ids);

        // then
        assertTrue(subTaskRepository.findById(subTask1.getId()).isEmpty());
        assertTrue(subTaskRepository.findById(subTask2.getId()).isEmpty());
    }
}
