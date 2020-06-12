package com.founder.dao.impl;


import cn.hutool.core.date.DateUtil;
import com.founder.dao.CloudSearchLogAnalysisDao;
import com.founder.model.UseModle;
import com.founder.util.MongoConfig;
import com.mongodb.MongoClient;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CloudSearchLogAnalysisImpl implements CloudSearchLogAnalysisDao {

    @Resource
    MongoConfig mongoConfig = null;

    public static final String APPDETAILVISITLOG = "APP_DETAIL_VISIT_TABLE";
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
        String startStr = DateUtil.format(startTime, "yyyyMMddHHmmss");
        String endStr = DateUtil.format(endTime, "yyyyMMddHHmmss");

        MongoTemplate mongoTemplate = getMongoTemplate();
        // 用来封装查询条件
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(Criteria.where("USEDATE").gte(startStr).lte(endStr));
        query.addCriteria(criteria.and("USEFUNCTION").is("全文智能搜索"));
        query.addCriteria(criteria.and("USEMOUDLE").in(new Object[]{"高级搜索", "普通搜索", "综合查询"}));
        System.out.println(query.toString());
        long count = mongoTemplate.count(query, long.class, APPDETAILVISITLOG);
        return count;
    }


    // yyyy-MM-dd HH:mm:ss
    @Override
    public List<UseModle> PerDaySearchNum(Date startTime, Date endTime) {

        String startStr = DateUtil.format(startTime, "yyyyMMddHHmmss");
        String endStr = DateUtil.format(endTime, "yyyyMMddHHmmss");
        MongoTemplate mongoTemplate = getMongoTemplate();

        Aggregation aggregation = Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("USEDATE").gte(startStr).lte(endStr)),
//                        Aggregation.group("USEDATE".substring(0,8)).count().as("num"),
                        Aggregation.project("USEDATE").andExpression("substr(USEDATE,0,8)").as("day"),
                        Aggregation.group("day").count().as("num"),
                        Aggregation.sort(Sort.Direction.DESC, "_id")
                );
        System.out.println(aggregation.toString());
        AggregationResults<UseModle> noRepeatDataList = mongoTemplate.aggregate(aggregation, APPDETAILVISITLOG, UseModle.class);
        List<UseModle> mappedResults = noRepeatDataList.getMappedResults();
        return mappedResults;
    }

    @Override
    public List<UseModle>PerMonthSearchNum(Date startTime, Date endTime) {
        String startStr = DateUtil.format(startTime, "yyyyMMddHHmmss");
        String endStr = DateUtil.format(endTime, "yyyyMMddHHmmss");
        MongoTemplate mongoTemplate = getMongoTemplate();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("USEDATE").gte(startStr).lte(endStr)),
                Aggregation.project("USEDATE").andExpression("substr(USEDATE,0,6)").as("month"),
                Aggregation.group("month").count().as("count"),
                Aggregation.sort(Sort.Direction.DESC, "_id")
        );


        System.out.println(aggregation.toString());
        AggregationResults<UseModle> noRepeatDataList = mongoTemplate.aggregate(aggregation, APPDETAILVISITLOG, UseModle.class);
        List<UseModle> mappedResults = noRepeatDataList.getMappedResults();
        return mappedResults;
    }


    @Override
    public List<UseModle> MaxSearchKeyWordSort(Date startTime, Date endTime) {


        return null;
    }

    /**
     * mongo 各单位检索次数查询
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<UseModle> PerUnitSearchNum(Date startTime, Date endTime) {
        String startStr = DateUtil.format(startTime, "yyyyMMddHHmmss");
        String endStr = DateUtil.format(endTime, "yyyyMMddHHmmss");
        MongoTemplate mongoTemplate = getMongoTemplate();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("USEDATE").gte(startStr).lte(endStr)),
                Aggregation.group("SSZZJGNAME").count().as("count"),
                Aggregation.sort(Sort.Direction.DESC, "count")
        );
        System.out.println(aggregation.toString());
        AggregationResults<UseModle> perUnitSearchNumList = mongoTemplate.aggregate(aggregation, APPDETAILVISITLOG, UseModle.class);
        List<UseModle> mappedResults = perUnitSearchNumList.getMappedResults();
        return mappedResults;
    }

    @Override
    public List<UseModle> PerUserSearchNumOfSort(Date startTime, Date endTime) {

        String startStr = DateUtil.format(startTime, "yyyyMMddHHmmss");
        String endStr = DateUtil.format(endTime, "yyyyMMddHHmmss");
        MongoTemplate mongoTemplate = getMongoTemplate();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("USEDATE").gte(startStr).lte(endStr)),
                Aggregation.group("USERXM").count().as("count"),
                Aggregation.sort(Sort.Direction.DESC, "count")
        );

        AggregationResults<UseModle> useSearchNumAggList = mongoTemplate.aggregate(aggregation, APPDETAILVISITLOG, UseModle.class);
        List<UseModle> mappedResults = useSearchNumAggList.getMappedResults();
        return mappedResults;
    }

    @Override
    public List<UseModle> SingleUserSearchContent(Date startTime, Date endTime) {
        return null;
    }
}
