package heech.server.heechtodo.api.todo.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTodoRequest {

    private String title;
    private Long order;
    private Boolean completed;
}
