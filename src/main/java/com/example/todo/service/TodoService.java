package com.example.todo.service;

import com.example.todo.domain.entity.SubTask;
import com.example.todo.domain.entity.TodoList;
import com.example.todo.domain.repository.SubTaskRepository;
import com.example.todo.domain.repository.TodoListRepository;
import com.example.todo.domain.entity.User;
import com.example.todo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserRepository userRepository;
    private final TodoListRepository todoListRepository;
    private final SubTaskRepository subTaskRepository;

    @Transactional
    public List<Map<String, Object>> getMyTodoListWithViews(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저 없음"));

        List<TodoList> todos = todoListRepository.findByOwner(user);
        List<Map<String, Object>> result = new ArrayList<>();

        for (TodoList todo : todos) {
            List<SubTask> subTasks = subTaskRepository.findByTodoList(todo);

            Map<String, Object> todoMap = new HashMap<>();
            todoMap.put("todo", todo);
            todoMap.put("subTasks", subTasks);
            todoMap.put("views", todo.getViewCount());

            result.add(todoMap);
        }

        return result;
    }

    public List<Map<String, Object>> getFollowingList(Long userId) {
        Set<User> followings = getFollowings(userId);

        List<Map<String, Object>> result = new ArrayList<>();
        for (User user : followings) {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getId());
            map.put("nickname", user.getNickname()); // 혹은 원하는 정보
            result.add(map);
        }

        return result;
    }


    public Set<User> getFollowings(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("해당 유저 없음"));

        return user.getFollowing();
    }
}
