package heech.server.heechtodo.core.todo.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import heech.server.heechtodo.core.todo.domain.Todo;
import heech.server.heechtodo.core.todo.dto.TodoSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static heech.server.heechtodo.core.todo.domain.QTodo.todo;

@Repository
public class TodoQueryRepository {

    private final JPAQueryFactory queryFactory;

    public TodoQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * todo 목록 조회
     */
    public Page<Todo> findTodos(TodoSearchCondition condition, Pageable pageable) {
        List<Todo> content = getTodoList(pageable);

        JPAQuery<Long> count = getTodoListCount();

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    /**
     * todo 목록
     */
    private List<Todo> getTodoList(Pageable pageable) {
        List<Todo> content = queryFactory
                .select(todo)
                .from(todo)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return content;
    }

    /**
     * todo 목록 카운트
     */
    private JPAQuery<Long> getTodoListCount() {
        JPAQuery<Long> count = queryFactory
                .select(todo.count())
                .from(todo);
        return count;
    }
}
