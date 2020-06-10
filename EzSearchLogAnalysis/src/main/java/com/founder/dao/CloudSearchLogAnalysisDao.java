package com.founder.dao;

import java.util.Date;
import java.util.List;

public interface CloudSearchLogAnalysisDao {
    //检索总次数（start_time,end_time）
    public Integer  AllCountOfHistorySearch(Date startTime, Date endTime);
    //每日检索次数
    public List<Integer> PerDaySearchNum(Date startTime,Date endTime);
    //每月检索次数
    public List<Integer> PerMonthSearchNum(Date startTime,Date endTime);
    //检索次数最多的关键字排序
    public List<Integer> MaxSearchKeyWordSort(Date startTime,Date endTime);
    //各单位检索次数排序
    public List<Integer> PerUnitSearchNum(Date startTime,Date endTime);
    //各用户检索次数排序
    public List<Integer> AllUserSearchNumOfSort(Date startTime,Date endTime);
    //某个用户在某段时间范围内，检索关键字次数排序、全部关键字的值
    public List<Integer> SingleUserSearchContent(Date startTime,Date endTime);

}
