package com.founder.api.exception;

import com.founder.api.enums.ErrorType;
import com.founder.api.response.BaseResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: workspace
 * @description: 全局异常处理控制器
 * @author: 刘宗强
 * @create: 2019-08-27 12:13
 **/
@ControllerAdvice
public class BaseControllerAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public BaseResponse<String> systemErr(Exception e){
        return new BaseResponse<String>(ErrorType.ERROR.getCode(),e.getMessage(),null);
    }

    /**
     * 不存在此资源
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(CustomException.NotFoundException.class)
    public BaseResponse<?> NotFoundException(CustomException.NotFoundException e){
        return new BaseResponse<>(e.getParams(),ErrorType.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<String> validateException(MethodArgumentNotValidException e){
        final List<String> errList = new ArrayList<>();
        e.getBindingResult().getAllErrors().stream().forEach(x-> {
            errList.add(x.getDefaultMessage());
        });
        return new BaseResponse<>(ErrorType.PARAM_INVALID.getCode(),null,errList.toString());
    }
}
