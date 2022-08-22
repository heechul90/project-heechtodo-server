package heech.server.heechtodo.core.todo.service;

import heech.server.heechtodo.core.common.dto.SearchCondition;
import heech.server.heechtodo.core.common.exception.EntityNotFound;
import heech.server.heechtodo.core.todo.domain.Todo;
import heech.server.heechtodo.core.todo.dto.TodoSearchCondition;
import heech.server.heechtodo.core.todo.dto.UpdateTodoParam;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    //SAVE_TODO_DATA
    public static final String TITLE = "test_title";
    public static final int ORDER = 0;

    //UPDATE_TODO_DATA
    public static final String UPDATE_TITLE = "update_title";
    public static final int UPDATE_ORDER = 1;
    public static final boolean UPDATE_COMPLETED = true;

    //VALIDATION_MESSAGE
    public static final String HAS_MESSAGE_STARTING_WITH = "존재하지 않는 Todo";

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
    @DisplayName(value = "todo 단건 조회")
    void findTodo() {
        //given
        Todo todo = getTodo(TITLE, ORDER);
        given(todoRepository.findById(any())).willReturn(Optional.ofNullable(todo));

        //when
        Todo findTodo = todoService.findTodo(any());

        //then
        assertThat(findTodo.getTitle()).isEqualTo(TITLE);
        assertThat(findTodo.getOrder()).isEqualTo(0);
        assertThat(findTodo.getCompleted()).isFalse();

        //verify
        verify(todoRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName(value = "todo 저장")
    void saveTodo() {
        //given
        Todo todo = getTodo(TITLE, ORDER);
        given(todoRepository.save(any())).willReturn(todo);

        //when
        Todo savedTodo = todoService.saveTodo(todo);

        //then
        assertThat(savedTodo.getTitle()).isEqualTo(TITLE);
        assertThat(savedTodo.getOrder()).isEqualTo(ORDER);
        assertThat(savedTodo.getCompleted()).isFalse();

        //verify
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    @DisplayName(value = "todo 수정")
    void updateTodo() {
        //given
        Todo todo = getTodo(TITLE, ORDER);
        given(todoRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(todo));

        UpdateTodoParam param = UpdateTodoParam.builder()
                .title(UPDATE_TITLE)
                .order(UPDATE_ORDER)
                .completed(UPDATE_COMPLETED)
                .build();

        //when
        todoService.updateTodo(any(Long.class), param);

        //then
        assertThatThrownBy(() -> todoService.updateTodo(todo.getId(), param))
                .isInstanceOf(EntityNotFound.class)
                .hasMessageStartingWith(HAS_MESSAGE_STARTING_WITH)
                .hasMessageContaining("id = " + todo.getId());
        assertThat(todo.getTitle()).isEqualTo(UPDATE_TITLE);
        assertThat(todo.getOrder()).isEqualTo(UPDATE_ORDER);
        assertThat(todo.getCompleted()).isTrue();

        //verify
        verify(todoRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName(value = "todo 삭제")
    void deleteTodo() {
    }
}