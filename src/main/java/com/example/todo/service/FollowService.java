package com.example.todo.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.example.todo.dto.UserResponseDto;

@Service
public class FollowService {

    // 팔로우 하기
    public void follow(Long userId) {
        // TODO: 유저 ID를 기반으로 팔로우 관계 저장
        System.out.println("팔로우 실행: " + userId);
    }

    // 언팔로우 하기
    public void unfollow(Long userId) {
        // TODO: 유저 ID를 기반으로 팔로우 관계 삭제
        System.out.println("언팔로우 실행: " + userId);
    }

    // 팔로우 목록 조회
    public List<UserResponseDto> getFollowings() {
        // TODO: 현재 로그인한 사용자가 팔로우한 유저 목록 조회
        System.out.println("팔로우 목록 조회");

        // 예시 데이터 반환
        List<UserResponseDto> list = new ArrayList<>();
        list.add(new UserResponseDto(1L, "test1@example.com"));
        list.add(new UserResponseDto(2L, "test2@example.com"));
        return list;
    }
}
