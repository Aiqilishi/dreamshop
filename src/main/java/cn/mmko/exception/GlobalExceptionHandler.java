package cn.mmko.exception;

import cn.mmko.enums.ResponseCode;
import cn.mmko.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理自定义业务异常
    @ExceptionHandler(AppException.class)
    public Response<Object> handleAppException(AppException ex) {
        log.warn("业务异常: code={}, info={}", ex.getCode(), ex.getInfo());
        return Response.builder()
                .code(ex.getCode())
                .info(ex.getInfo())
                .data(null)
                .build();
    }

    // 处理所有未捕获的异常
    @ExceptionHandler(Exception.class)
    public Response<Object> handleException(Exception ex) {
        log.error("系统异常: ", ex);
        return Response.builder()
                .code(ResponseCode.UN_ERROR.getCode())
                .info(ResponseCode.UN_ERROR.getInfo())
                .data(null)
                .build();
    }
}