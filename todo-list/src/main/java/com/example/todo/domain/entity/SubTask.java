package com.example.todo.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SubTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private SubTaskStatus status = SubTaskStatus.SUBTASK_BEFORE; // 기본값 초기화

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id") // 외래 키 명시
    @ToString.Exclude // 순환 참조 방지
    private Todo todo;

    public SubTask(String title, Todo todo) {
        this.title = title;
        this.todo = todo;
        this.status = SubTaskStatus.SUBTASK_BEFORE;
    }


}
