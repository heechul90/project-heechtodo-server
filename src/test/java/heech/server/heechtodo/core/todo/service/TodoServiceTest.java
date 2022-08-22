package heech.server.heechtodo.core.todo.service;

import heech.server.heechtodo.core.common.dto.SearchCondition;
import heech.server.heechtodo.core.todo.domain.Todo;
import heech.server.heechtodo.core.todo.dto.TodoSearchCondition;
import heech.server.heechtodo.core.todo.repository.TodoQueryRepository;
import heech.server.heechtodo.core.todo.repository.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    public static final String TITLE = "test_title";
    public static final int ORDER = 0;

    private Todo getTodo(String title, int order) {
        return Todo.createTodoBuilder()
                .title(title)
                .order(order)
                .build();
    }

    @InjectMocks TodoService todoService;

    @Mock TodoRepository todoRepository;

    @Mock TodoQueryRepository todoQueryRepository;

    @Test
    @DisplayName(value = "todo 목록 조회")
    void findTodos() {
        //given
        List<Todo> todos = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            todos.add(getTodo(TITLE + i, ORDER + 1));
        }

        Todo todo = getTodo(TITLE, ORDER);
        given(todoQueryRepository.findTodos(any(TodoSearchCondition.class), any())).willReturn(new PageImpl<>(todos));

        //when
        TodoSearchCondition condition = new TodoSearchCondition();
        condition.setSearchCondition(SearchCondition.TITLE);
        condition.setSearchKeyword("test");
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Todo> content = todoService.findTodos(condition, pageRequest);

        //then
        assertThat(content.getTotalElements()).isEqualTo(30);
        assertThat(content.getContent().size()).isEqualTo(30);
        assertThat(content.getContent()).extracting("title").contains(TITLE + 1, TITLE + 29);

        //verify
        verify(todoQueryRepository, times(1)).findTodos(any(), any());
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