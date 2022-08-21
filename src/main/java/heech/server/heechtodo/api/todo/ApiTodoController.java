package heech.server.heechtodo.api.todo;

import heech.server.heechtodo.core.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/todo")
public class ApiTodoController {

    private final TodoService todoService;

}
