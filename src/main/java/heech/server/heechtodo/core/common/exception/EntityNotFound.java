package heech.server.heechtodo.core.common.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFound extends CommonException {

    public EntityNotFound(String entityName, Long id) {
        super("존재하지 않는 " + entityName + "입니다. id = " + id);
    }

    public EntityNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }
}
