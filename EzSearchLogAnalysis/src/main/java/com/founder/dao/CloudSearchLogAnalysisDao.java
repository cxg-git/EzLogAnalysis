package com.founder.dao;

import com.founder.model.USEMODLE;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CloudSearchLogAnalysisDao {
    //检索总次数（start_time,end_time）
    public Long  AllCountOfHistorySearch(Date startTime, Date endTime);
    //每日检索次数
    public List<USEMODLE> PerDaySearchNum(Date startTime, Date endTime);
    //每月检索次数
    public Map<String,Integer> PerMonthSearchNum(String startTime,String endTime);
    //检索次数最多的关键字排序
    public Map<String,Integer> MaxSearchKeyWordSort(String startTime,String endTime);
    //各单位检索次数排序
    public Map<String,Integer> PerUnitSearchNum(String startTime,String endTime);
    //各用户检索次数排序
    public Map<String,Integer> AllUserSearchNumOfSort(String startTime,String endTime);
    //某个用户在某段时间范围内，检索关键字次数排序、全部关键字的值
    public Map<String,Integer> SingleUserSearchContent(String startTime,String endTime);
}
