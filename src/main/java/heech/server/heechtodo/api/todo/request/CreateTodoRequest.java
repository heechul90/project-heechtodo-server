package heech.server.heechtodo.api.todo.request;

import heech.server.heechtodo.core.common.exception.JsonInvalidRequest;
import heech.server.heechtodo.core.common.json.Error;
import heech.server.heechtodo.core.todo.domain.Todo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CreateTodoRequest {

    @NotBlank
    private String title;

    @PositiveOrZero
    private int order;

    public Todo toEntity() {
        return Todo.createTodoBuilder()
                .title(this.title)
                .order(this.order)
                .build();
    }

    public void validate() {
        List<Error> errors = new ArrayList<>();

        if (this.title.contains("바보")) {
            errors.add(new Error("title", "제목에 \"바보\"를 포함할 수 없습니다."));
        }

        if (errors.size() > 0) {
            throw new JsonInvalidRequest(errors);
        }
    }
}
