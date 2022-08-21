package heech.server.heechtodo.api.todo.response;

import heech.server.heechtodo.core.todo.domain.Todo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoResponse {

    private Long todoId;
    private String todoTitle;
    private int order;
    private Boolean completed;
    private String url;

    public TodoResponse(Todo todo) {
        this.todoId = todo.getId();
        this.todoTitle = todo.getTitle();
        this.order = todo.getOrder();
        this.completed = todo.getCompleted();
        this.url = "http://localhost:9002" + "/api/todos/" + this.todoId;
    }

}
