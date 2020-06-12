package com.founder.dao;

import com.founder.model.UseModle;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CloudSearchLogAnalysisDao {
    //检索总次数（start_time,end_time）
    public Long  AllCountOfHistorySearch(Date startTime, Date endTime);
    //每日检索次数
    public List<UseModle> PerDaySearchNum(Date startTime, Date endTime);
    //每月检索次数
    public List<UseModle> PerMonthSearchNum(Date startTime, Date endTime);
    //检索次数最多的关键字排序
    public List<UseModle> MaxSearchKeyWordSort(Date startTime, Date endTime);
    //各单位检索次数排序
    public List<UseModle> PerUnitSearchNum(Date startTime, Date endTime);
    //各用户检索次数排序
    public List<UseModle> PerUserSearchNumOfSort(Date startTime, Date endTime);
    //某个用户在某段时间范围内，检索关键字次数排序、全部关键字的值
    public List<UseModle> SingleUserSearchContent(Date startTime, Date endTime);
}
