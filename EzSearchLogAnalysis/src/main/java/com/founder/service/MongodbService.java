package com.founder.service;

import com.founder.model.AppDetailVisitLog;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MongodbService {
    @Resource
    private MongoTemplate mongoTemplate;

    public AppDetailVisitLog insert(AppDetailVisitLog appDetailVisitLog){
        // insert 方法不提供级联类的保存，所以及级联类需要另外处理
        return mongoTemplate.insert(appDetailVisitLog);
    }

    public void removeByName(String name){
        AppDetailVisitLog appDetailVisit = mongoTemplate.findOne(Query.query(Criteria.where("name").is(name)), AppDetailVisitLog.class);
        String optiondata = appDetailVisit.getOPTIONDATA();
    }


}
