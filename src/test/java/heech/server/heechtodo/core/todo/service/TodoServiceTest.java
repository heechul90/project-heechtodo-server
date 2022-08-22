package heech.server.heechtodo.core.todo.service;

import heech.server.heechtodo.core.todo.repository.TodoQueryRepository;
import heech.server.heechtodo.core.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks TodoService todoService;

    @Mock TodoRepository todoRepository;

    @Mock TodoQueryRepository todoQueryRepository;

    @Test
    void findTodos() {
    }

    @Test
    void findTodo() {
    }

    @Test
    void saveTodo() {
    }

    @Test
    void updateTodo() {
    }

    @Test
    void deleteTodo() {
    }
}