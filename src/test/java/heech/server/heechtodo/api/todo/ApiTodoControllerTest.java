package heech.server.heechtodo.api.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import heech.server.heechtodo.api.todo.request.CreateTodoRequest;
import heech.server.heechtodo.core.common.dto.SearchCondition;
import heech.server.heechtodo.core.todo.domain.Todo;
import heech.server.heechtodo.core.todo.dto.TodoSearchCondition;
import heech.server.heechtodo.core.todo.service.TodoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@WebMvcTest(ApiTodoController.class)
class ApiTodoControllerTest {

    //SAVE_TODO_DATA
    public static final String TITLE = "test_title";
    public static final int ORDER = 0;

    //UPDATE_TODO_DATA
    public static final String UPDATE_TITLE = "update_title";
    public static final int UPDATE_ORDER = 1;
    public static final boolean UPDATE_COMPLETED = true;

    //VALIDATION_MESSAGE
    public static final String HAS_MESSAGE_STARTING_WITH = "존재하지 않는 Todo";
    public static final String API_FIND_TODOS = "/api/todos";
    public static final String API_FIND_TODO = "/api/todos/{id}";
    public static final String API_SAVE_TODO = "/api/todos";

    @Autowired private MockMvc mockMvc;

    @MockBean private TodoService todoService;

    @Autowired ObjectMapper objectMapper;

    private Todo getTodo(String title, int order) {
        return Todo.createTodoBuilder()
                .title(title)
                .order(order)
                .build();
    }

    @Test
    @DisplayName(value = "todo 목록 조회")
    void findTodos() throws Exception {
        //given
        List<Todo> todos = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            todos.add(getTodo(TITLE + i, ORDER + i));
        }

        given(todoService.findTodos(any(TodoSearchCondition.class), any(PageRequest.class))).willReturn(new PageImpl(todos));

        TodoSearchCondition condition = new TodoSearchCondition();
        condition.setSearchCondition(SearchCondition.TITLE);
        condition.setSearchKeyword("test");
        PageRequest pageRequest = PageRequest.of(0, 30);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_TODOS)
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", String.valueOf(pageRequest.getOffset()))
                .param("size", String.valueOf(pageRequest.getPageSize()))
                .param("searchCondition", condition.getSearchCondition().name())
                .param("searchKeyword", condition.getSearchKeyword())
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", Matchers.is(30)))
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(todoService, times(1)).findTodos(any(), any());
    }

    @Test
    @DisplayName(value = "todo 단건 조회")
    void findTodo() throws Exception {
        //given
        Todo todo = getTodo(TITLE, ORDER);
        given(todoService.findTodo(any(Long.class))).willReturn(todo);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(API_FIND_TODO, 0L)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.todoTitle").value(TITLE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.order").value(ORDER))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.completed").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.url").value("http://localhost:9002" + API_FIND_TODOS + "/null"))
                .andDo(MockMvcResultHandlers.print());

        //verify
    }

    @Test
    @DisplayName(value = "todo 저장")
    void saveTodo() throws Exception {
        //given
        Todo todo = getTodo(TITLE, ORDER);
        given(todoService.saveTodo(any())).willReturn(todo);

        CreateTodoRequest request = new CreateTodoRequest();
        request.setTitle(TITLE);
        request.setOrder(ORDER);

        //when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(API_SAVE_TODO)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(HttpStatus.OK.getReasonPhrase()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.todoId").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.todoTitle").value(TITLE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.order").value(ORDER))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.completed").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.url").value("http://localhost:9002" + API_FIND_TODOS + "/null"))
                .andDo(MockMvcResultHandlers.print());

        //verify
        verify(todoService, times(1)).saveTodo(any());
    }

    @Test
    @DisplayName(value = "todo 수정")
    void updateTodo() {
    }

    @Test
    @DisplayName(value = "todo 삭제")
    void deleteTodo() {
    }
}