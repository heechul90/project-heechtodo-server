package heech.server.heechtodo.core.common.exception;

import heech.server.heechtodo.core.common.json.Error;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class CommonException extends RuntimeException {

    List<Error> errors = new ArrayList<>();

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract HttpStatus status();

    public void addError(List<Error> errors) {
        this.errors = errors;
    }

}
