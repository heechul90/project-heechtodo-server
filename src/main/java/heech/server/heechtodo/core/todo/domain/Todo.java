package heech.server.heechtodo.core.todo.domain;

import heech.server.heechtodo.core.todo.dto.UpdateTodoParam;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "todo_order", nullable = false)
    private int order;

    @Column(nullable = false)
    private Boolean completed;

    //===생성 메서드===//
    /**
     * todo Entity 생성
     */
    @Builder(builderMethodName = "createTodoBuilder", builderClassName = "createTodoBuilder")
    public Todo(String title, int order) {
        this.title = title;
        this.order = order;
        this.completed = false;
    }

    /**
     * todo Entity 수정
     */
    @Builder(builderMethodName = "updateTodoBuilder", builderClassName = "updateTodoBuilder")
    public void updateTodo(UpdateTodoParam param) {

    }
}
