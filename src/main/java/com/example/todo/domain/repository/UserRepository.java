package com.example.todo.repository;

import com.example.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // 팔로우하는 유저 리스트 조회
    Optional<User> findWithFollowingById(Long id);  // 필요시 @EntityGraph로 eager하게도 가능
}
