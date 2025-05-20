package com.example.todo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "todo_list")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 기존 필드들
    private String title;

    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    // 조회수 컬럼 추가
    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubTask> subTasks = new ArrayList<>();
}
