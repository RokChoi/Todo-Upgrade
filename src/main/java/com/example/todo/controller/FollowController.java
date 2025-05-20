package com.example.todo.controller;

import com.example.todo.dto.UserResponseDto;
import com.example.todo.service.FollowService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{userId}")
    @Operation(summary = "팔로우하기", description = "특정 사용자를 팔로우합니다.")
    public ResponseEntity<Void> followUser(@PathVariable Long userId) {
        followService.follow(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "언팔로우하기", description = "특정 사용자를 언팔로우합니다.")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long userId) {
        followService.unfollow(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    @Operation(summary = "팔로우 목록 조회", description = "내가 팔로우한 사용자 목록을 조회합니다.")
    public ResponseEntity<List<UserResponseDto>> getFollowList() {
        return ResponseEntity.ok(followService.getFollowings());
    }
}
