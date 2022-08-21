package heech.server.heechtodo.api.todo;

import heech.server.heechtodo.api.todo.request.CreateTodoRequest;
import heech.server.heechtodo.api.todo.request.UpdateTodoRequest;
import heech.server.heechtodo.api.todo.response.CreateTodoResponse;
import heech.server.heechtodo.api.todo.response.TodoResponse;
import heech.server.heechtodo.api.todo.response.UpdateTodoResponse;
import heech.server.heechtodo.core.common.json.JsonResult;
import heech.server.heechtodo.core.todo.domain.Todo;
import heech.server.heechtodo.core.todo.dto.TodoSearchCondition;
import heech.server.heechtodo.core.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/todos")
public class ApiTodoController {

    private final TodoService todoService;

    /**
     * todo 목록 조회
     */
    @GetMapping
    public JsonResult findTodos(TodoSearchCondition condition, Pageable pageable) {
        Page<Todo> content = todoService.findTodos(condition, pageable);

        if (content.getContent().size() == 0) {
            return JsonResult.OK("조회된 목록이 없습니다.");
        }
        List<TodoResponse> todos = content.getContent().stream()
                .map(todo -> new TodoResponse(todo))
                .collect(Collectors.toList());

        return JsonResult.OK(todos);
    }

    /**
     * todo 단건 조회
     */
    @GetMapping(value = "/{id}")
    public JsonResult findTodo(@PathVariable("id") Long todoId) {
        Todo findTodo = todoService.findTodo(todoId);
        TodoResponse todo = new TodoResponse(findTodo);
        return JsonResult.OK(todo);
    }

    /**
     * todo 저장
     */
    @PostMapping
    public JsonResult saveTodo(@RequestBody @Validated CreateTodoRequest request) {

        //validation check
        request.validate();

        Todo savedTodo = todoService.saveTodo(request.toEntity());
        return JsonResult.OK(new CreateTodoResponse(savedTodo));
    }

    /**
     * todo 수정
     */
    @PutMapping(value = "/{id}")
    public JsonResult updateTodo(@PathVariable("id") Long todoId,
                                 @RequestBody @Validated UpdateTodoRequest request) {

        //validation check
        request.validate();

        todoService.updateTodo(todoId, request.toUpdateTodoParam());
        Todo updatedTodo = todoService.findTodo(todoId);

        return JsonResult.OK(new UpdateTodoResponse(updatedTodo));
    }

    /**
     * todo 삭제
     */
    @DeleteMapping(value = "/{id}")
    public JsonResult deleteTodo(@PathVariable("id") Long todoId) {

        todoService.deleteTodo(todoId);

        return JsonResult.OK();
    }

}
