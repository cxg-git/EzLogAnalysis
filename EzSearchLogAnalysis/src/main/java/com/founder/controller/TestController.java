package com.founder.controller;


import com.founder.model.User;
import com.founder.api.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

/**
 * @program: log-demo
 * @description:
 * @author: 刘宗强
 * @create: 2019-08-26 12:02
 **/
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

   @GetMapping("")
   public String test(){
       Map<String,Object> map=new HashMap<>();
       map.put("id","12");
      throw new CustomException.NotFoundException(map);
   }

   @PostMapping("/addUser")
    public User testPost(@RequestBody @Validated User user){
        return user;
   }


   @GetMapping("/getUser")
    public User getUser(){
       User user=new User("liuzongqiang",22);
       return user;
   }

   @GetMapping("/getStr")
    public String getStr(){
       return "hello";
   }
}
