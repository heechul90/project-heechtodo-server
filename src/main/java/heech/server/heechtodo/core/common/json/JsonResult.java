package heech.server.heechtodo.core.common.json;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class JsonResult<T> {

    private LocalDateTime transaction_time;
    private HttpStatus status;
    private String message = "";

    private List<Error> errors;
    private T data;

    public static <T> JsonResult<T> OK() {
        return (JsonResult<T>) JsonResult.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.OK)
                .build();
    }

    public static <T> JsonResult<T> OK(T data) {
        return (JsonResult<T>) JsonResult.builder()
                .transaction_time(LocalDateTime.now())
                .status(HttpStatus.OK)
                .data(data)
                .build();
    }

    public static <T> JsonResult<T> ERROR(HttpStatus status, String message, List<Error> errors) {
        return (JsonResult<T>) JsonResult.builder()
                .transaction_time(LocalDateTime.now())
                .status(status)
                .message(message)
                .errors(errors)
                .build();
    }

}
