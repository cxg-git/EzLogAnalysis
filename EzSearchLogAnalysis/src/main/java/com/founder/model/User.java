package com.founder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: workspace
 * @description:
 * @author: 刘宗强
 * @create: 2019-08-26 16:02
 **/
@Data
@AllArgsConstructor
public class User {

    private String userName;

    @NotNull(message = "年龄不能为空！")
    private Integer Age;
}
