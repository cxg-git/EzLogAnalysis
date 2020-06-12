package com.founder.controller;


import cn.hutool.core.date.DateUtil;
import com.founder.dao.impl.CloudSearchLogAnalysisImpl;
import com.founder.model.TimeModel;
import com.founder.model.UseModle;
import com.founder.model.User;
import com.founder.api.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
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

    @Autowired
    CloudSearchLogAnalysisImpl cloudSearchLogAnalysis = null;

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

   @PostMapping("/history/search")
   public long getHistroySearchCountByTime(@RequestBody  TimeModel timeModel){
       Date end = DateUtil.parseDate(timeModel.getEndTime());
       Date start = DateUtil.parseDate(timeModel.getStartTime());
       Long count = cloudSearchLogAnalysis.AllCountOfHistorySearch(start, end);
       return count;
   }

   @PostMapping("/history/day")
   public List<UseModle> getPerDaySearchNumByTime(@RequestBody TimeModel timeModel){
       Date end = DateUtil.parseDate(timeModel.getEndTime());
       Date start = DateUtil.parseDate(timeModel.getStartTime());
       List<UseModle> usemodles = cloudSearchLogAnalysis.PerDaySearchNum(start, end);
       return usemodles;
   }

    @PostMapping("/history/month")
    public List<UseModle> getPerMonthSearchNumByTime(@RequestBody TimeModel timeModel){
        Date end = DateUtil.parseDate(timeModel.getEndTime());
        Date start = DateUtil.parseDate(timeModel.getStartTime());
        List<UseModle> usemodles = cloudSearchLogAnalysis.PerMonthSearchNum(start, end);
        return usemodles;
    }

    @PostMapping("/history/unit")
    public List<UseModle> getPerUnitSearchNumByTime(@RequestBody TimeModel timeModel){
        System.out.println(timeModel);
        Date end = DateUtil.parseDate(timeModel.getEndTime());
        Date start = DateUtil.parseDate(timeModel.getStartTime());
        List<UseModle> usemodles = cloudSearchLogAnalysis.PerUnitSearchNum(start, end);
        return usemodles;
    }

    @PostMapping("/history/user")
    public List<UseModle> getPerUserSearchNumByTime(@RequestBody TimeModel timeModel){
        Date end = DateUtil.parseDate(timeModel.getEndTime());
        Date start = DateUtil.parseDate(timeModel.getStartTime());
        List<UseModle> usemodles = cloudSearchLogAnalysis.PerUserSearchNumOfSort(start, end);
       return usemodles;
    }

}
