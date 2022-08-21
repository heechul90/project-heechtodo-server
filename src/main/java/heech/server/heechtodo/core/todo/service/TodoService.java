package heech.server.heechtodo.core.todo.service;

import heech.server.heechtodo.core.todo.domain.Todo;
import heech.server.heechtodo.core.todo.dto.TodoSearchCondition;
import heech.server.heechtodo.core.todo.dto.UpdateTodoParam;
import heech.server.heechtodo.core.todo.repository.TodoQueryRepository;
import heech.server.heechtodo.core.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoQueryRepository todoQueryRepository;

    /**
     * todo 목록 조회
     */
    public Page<Todo> findTodos(TodoSearchCondition condition, Pageable pageable) {
        return todoQueryRepository.findTodos(condition, pageable);
    }

    /**
     * todo 단건 조회
     */
    public Todo findTodo(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new NoSuchElementException());
    }

    /**
     * todo 저장
     */
    public Todo saveTodo(Todo todo) {
        Todo savedTodo = todoRepository.save(todo);
        return savedTodo;
    }

    /**
     * todo 수정
     */
    public void updateTodo(Long todoId, UpdateTodoParam param) {
        Todo findTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NoSuchElementException());
        findTodo.updateTodoBuilder()
                .param(param)
                .build();
    }

    /**
     * todo 삭제
     */
    public void deleteTodo(Long todoId) {
        Todo findTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NoSuchElementException());
        todoRepository.delete(findTodo);
    }
}
