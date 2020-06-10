package com.founder.dao;

import java.util.Date;
import java.util.List;

public interface CloudAnalysisLogAnalysisDao {
    //模型总数量
    public Integer CreatedModelNum(Date startTime,Date endTime);
    //开通用户总数量
    public Integer RegisteredUserNum(Date startTime,Date endTime);
    //各单位用户数量统计
    public List<Integer> PerUnitOfUserNum(Date startTime, Date endTime);
    //各单位模型数量
    public List<Integer> PerUnitOfModelNum(Date startTime,Date endTime);
    //各用户模型数量
    public List<Integer> PerUserOfModelNum(Date startTime,Date endTime);
    //模型发布成服务数量
    public List<Integer> ModelPushedServiceNum(Date startTime,Date endTime);
    //各单位发布成服务模型数量

    //模型执行总次数

    //各模型执行次数排序

    //模型推送总次数

    //各模型推送次数排序

    //定时推送模型数量、实时推送模型数量分类统计

}
