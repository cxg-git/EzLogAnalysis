package com.founder.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @program: workspace
 * @description: 自定义异常
 * @author: 刘宗强
 * @create: 2019-08-27 11:56
 **/
public class CustomException {
    /**
     * 不存在此资源
     */
    @Data
    @Accessors(chain = true)
    @AllArgsConstructor
    public static class NotFoundException extends RuntimeException{
        private Map<String,Object> params;
    }


}
