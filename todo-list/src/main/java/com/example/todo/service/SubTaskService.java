package com.example.todo.service;

import com.example.todo.domain.entity.SubTask;
import com.example.todo.domain.entity.SubTaskStatus;
import com.example.todo.domain.repository.SubTaskRepository;
import com.example.todo.exception.SubTaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubTaskService {

    private final SubTaskRepository subTaskRepository;

    public SubTask createSubTask(SubTask subTask) {
        return subTaskRepository.save(subTask);
    }

    public void updateSubTaskStatus(Long id, SubTaskStatus status) {
        if (status != SubTaskStatus.SUBTASK_BEFORE && status != SubTaskStatus.SUBTASK_COMPLETE) {
            throw new IllegalArgumentException("SubTask는 진행전 또는 완료 상태만 가능합니다.");
        }

        SubTask subTask = subTaskRepository.findById(id)
                .orElseThrow(SubTaskNotFoundException::new);  // 여기 수정
        subTask.setStatus(status);
        subTaskRepository.save(subTask);
    }

    public Optional<SubTask> findById(Long id) {
        return subTaskRepository.findById(id);
    }

    public void deleteSubTask(Long id) {
        SubTask subTask = subTaskRepository.findById(id)
                .orElseThrow(SubTaskNotFoundException::new);  // 여기 수정
        subTaskRepository.delete(subTask);
    }

    public List<Long> deleteMultipleSubTasks(List<Long> ids) {
        for (Long id : ids) {
            SubTask subTask = subTaskRepository.findById(id)
                    .orElseThrow(SubTaskNotFoundException::new);
            subTaskRepository.delete(subTask);
        }
        return ids;
    }

}
