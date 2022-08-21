package heech.server.heechtodo.core.todo.repository;

import heech.server.heechtodo.core.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
