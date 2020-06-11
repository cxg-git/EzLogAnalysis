package com.founder.dao.impl;


import cn.hutool.core.date.DateUtil;
import com.founder.dao.CloudSearchLogAnalysisDao;
import com.founder.model.USEMODLE;
import com.founder.util.MongoConfig;
import com.mongodb.MongoClient;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CloudSearchLogAnalysisImpl implements CloudSearchLogAnalysisDao {

    MongoConfig mongoConfig;

    public static final String APPDETAILVISITLOG = "APPDETAILVISITLOG";
    private Date startTime;
    private Date endTime;

    /**`
     * 获取mongoTemplate的客户端
     * @return
     */
    private MongoTemplate getMongoTemplate(){
        MongoClient mongoClient = mongoConfig.mongoClient();
        MongoTemplate mongoTemplate = mongoConfig.mongoTemplate(mongoClient);
        return mongoTemplate;
    }

    @Override
    public Long AllCountOfHistorySearch(Date startTime, Date endTime) {
        String startStr = DateUtil.format(startTime, "yyyyMMdd");
        String endStr = DateUtil.format(endTime, "yyyyMMdd");

        MongoTemplate mongoTemplate = getMongoTemplate();
        // 用来封装查询条件
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(Criteria.where("USEDATE").gte(startStr));
        query.addCriteria(Criteria.where("USEDATE").lte(endStr));
        query.addCriteria(criteria.and("USEFUNCTION").is("全文智能搜索"));
        query.addCriteria(criteria.and("USEMOUDLE").is("普通搜索"));

        long count = mongoTemplate.count(query, Integer.class, APPDETAILVISITLOG);
        return count;
    }

    @Test
    public void test () {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));

    }

    // yyyy-MM-dd HH:mm:ss
    @Override
    public List<USEMODLE> PerDaySearchNum(Date startTime, Date endTime) {

        String startStr = DateUtil.format(startTime, "yyyyMMdd");
        String endStr = DateUtil.format(endTime, "yyyyMMdd");
        MongoTemplate mongoTemplate = getMongoTemplate();


//        GroupOperation useFunctionGroup = Aggregation.group("USEFUNCTION").count().as("num").sum("totalProduct").as("count");
        Aggregation aggregation = Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("USEDATE").gte(startStr).lte(endTime)),
                        Aggregation.group("USEFUNCTION").count().as("num").sum("totalProduct").as("count")
                );
        AggregationResults<USEMODLE> noRepeatDataList = mongoTemplate.aggregate(aggregation, APPDETAILVISITLOG,USEMODLE.class);
        List<USEMODLE> mappedResults = noRepeatDataList.getMappedResults();
        return mappedResults;
    }

    @Override
    public Map<String, Integer> PerMonthSearchNum(String startTime, String endTime) {

        return null;
    }

    @Override
    public Map<String, Integer> MaxSearchKeyWordSort(String startTime, String endTime) {
        return null;
    }

    @Override
    public Map<String, Integer> PerUnitSearchNum(String startTime, String endTime) {
        return null;
    }

    @Override
    public Map<String, Integer> AllUserSearchNumOfSort(String startTime, String endTime) {
        return null;
    }

    @Override
    public Map<String, Integer> SingleUserSearchContent(String startTime, String endTime) {
        return null;
    }
}
