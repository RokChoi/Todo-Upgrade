package com.example.todo.controller;

import com.example.todo.domain.entity.User;
import com.example.todo.dto.UserLoginRequest;
import com.example.todo.service.UserService;
import com.example.todo.exception.UserNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collections;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginRequest request, HttpSession session) {

        User foundUser = userService.findByEmail(request.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!userService.matches(request.getPassword(), foundUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(foundUser, null, Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        Map<String, String> response = new HashMap<>();
        response.put("message", "로그인 성공");
        return ResponseEntity.ok(response);

    }

    @RestController
    @RequestMapping("/api/users")
    @RequiredArgsConstructor
    public class UserController {

        private final UserRepository userRepository;

        // 팔로우
        @PostMapping("/{id}/follow")
        public ResponseEntity<String> follow(@PathVariable Long id, @RequestParam Long targetId) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("유저 없음"));
            User target = userRepository.findById(targetId)
                    .orElseThrow(() -> new RuntimeException("팔로우 대상 없음"));

            user.follow(target);
            userRepository.save(user);  // 연관 관계 저장

            return ResponseEntity.ok("팔로우 성공");
        }

        // 언팔로우
        @DeleteMapping("/{id}/unfollow")
        public ResponseEntity<String> unfollow(@PathVariable Long id, @RequestParam Long targetId) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("유저 없음"));
            User target = userRepository.findById(targetId)
                    .orElseThrow(() -> new RuntimeException("언팔로우 대상 없음"));

            user.unfollow(target);
            userRepository.save(user);  // 연관 관계 삭제 반영

            return ResponseEntity.ok("언팔로우 성공");
        }
    }


}
