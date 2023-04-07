package com.bus.businessdiaryapp.advice;

import com.bus.businessdiaryapp.util.JsonResult;
import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@ControllerAdvice(basePackages = {"com.bus.businessdiaryapp.web"})
@Slf4j
public class WebControllerAdvice {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    JsonResult handleControllerException(HttpServletRequest request, Throwable th) {
        HttpStatus status = getStatus(request);
        log.error("Controller Path: [{}]", request.getRequestURI(), th);
        return JsonResult.fail(status.toString(), "系统繁忙稍等再试");
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * annotation @Valid 对象中的字段验证失败
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public JsonResult methodArgumentNotValidExceptionExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.warn(e.getMessage());
        List<String> msgs = Lists.newLinkedList();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                msgs.add(String.format("%s", fieldError.getDefaultMessage()));
            } else {
                msgs.add(e.getMessage());
            }
        }
        String message = String.join(";", msgs);
        log.error("{}",message,e);
        return JsonResult.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), message);
    }
}
