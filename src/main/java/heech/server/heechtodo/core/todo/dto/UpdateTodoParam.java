package heech.server.heechtodo.core.todo.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UpdateTodoParam {

    private String title;
    private int order;
    private boolean completed;
}
