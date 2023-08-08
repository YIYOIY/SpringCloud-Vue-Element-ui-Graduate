package com.yoi.exception;

import com.yoi.entity.ReturnInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.Objects;

/**
 * @author 游弋
 * @create 2023-08-06 8:32
 */
@RestControllerAdvice
@Slf4j
public class GlobalException {
    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ReturnInfo<Exception> methodArgumentNotValid(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());
        return new ReturnInfo<>(400, exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 参数格式有误
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    protected ReturnInfo<Exception> typeMismatch(MethodArgumentTypeMismatchException e) {
        String msg = "参数转换失败，方法："+ Objects.requireNonNull(e.getParameter().getMethod()).getName()
                +",期望参数类型："+e.getParameter().getParameterType()
                +",参数："+e.getName()
                +",信息："+e.getMessage();
        log.error(msg);
        return new ReturnInfo<>(400, "参数格式有误");
    }


    /**
     * 处理所有RequestParam注解数据验证异常
     */
    @ExceptionHandler(BindException.class)
    public ReturnInfo<Exception> handleBindException(BindException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null;
        log.warn("必填校验异常:{}({})", fieldError.getDefaultMessage(), fieldError.getField());
        return new ReturnInfo<>(400, ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 缺少参数
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ReturnInfo<Exception> missingServletRequestParameter(MissingServletRequestParameterException exception) {
        log.warn(exception.getMessage());
        return new ReturnInfo<>(400, "缺少参数");
    }

    /**
     * 不支持的请求类型
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ReturnInfo<Exception> httpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
        log.warn(exception.getMessage());
        return new ReturnInfo<>(400, "不支持的请求类型");
    }


    /**
     * 空指针异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ReturnInfo<Exception> exceptionHandler(NullPointerException exception) {
        log.warn(exception.getMessage());
        return new ReturnInfo<>(400, "空指针异常");
    }


    /**
     *唯一键键列
     */
    @ExceptionHandler(DataAccessException.class)
    protected ReturnInfo<Exception> SQLIntegrityConstraintViolationException(Exception exception) {
        log.error("GlobalExceptionHandler.exceptionHandler , 异常信息", exception);
        return new ReturnInfo<>(403, "啊！账户已经被占用了");
    }


    /**
     * 其他异常
     */
    @ExceptionHandler({HttpClientErrorException.class, IOException.class, Exception.class})
    protected ReturnInfo<Exception> commonException(Exception exception) {
        log.error("GlobalExceptionHandler.exceptionHandler , 异常信息", exception);
        return new ReturnInfo<>(400, "参数异常");
    }

}