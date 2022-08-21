package heech.server.heechtodo.core.common.web;

import heech.server.heechtodo.core.common.exception.CommonException;
import heech.server.heechtodo.core.common.json.Error;
import heech.server.heechtodo.core.common.json.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CommonApiControllerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<Error> errors = e.getFieldErrors().stream()
                .map(error -> Error.builder()
                        .fieldName(error.getField())
                        .errorMessage(error.getDefaultMessage())
                        .build()
                ).collect(Collectors.toList());

        return JsonResult.ERROR(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", errors);
    }

    @ExceptionHandler(CommonException.class)
    public JsonResult apiCommonException(CommonException e) {
        return JsonResult.ERROR(e.status(), e.getMessage(), e.getErrors());
    }
}
