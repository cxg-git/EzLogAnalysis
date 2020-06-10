package com.founder.util;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoConfig {

    @Value("${spring.mongo.dbname}")
    private String dbname;
    @Value("${spring.mongo.host}")
    private String MongoHost;
    @Value("${spring.mongo.port}")
    private String MongoPort;
    @Value("${spring.mongo.connectionsPerHost}")
    private String connectionsPerHost;

    /**
     * 设置连接参数
     */
    private MongoClientOptions getMongoClientOptions() {
        MongoClientOptions.Builder builder = MongoClientOptions.builder();
        // todo 添加其他参数配置
        //最大连接数
        builder.connectionsPerHost(Integer.valueOf(connectionsPerHost));
        MongoClientOptions options = builder.readPreference(ReadPreference.nearest()).build();
        return options;
    }

    /**
     *
     * @return
     */
    @Bean
    public MongoClient mongoClient() {

        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectTimeout(10000);
        builder.maxWaitTime(120000);
        MongoClientOptions mongoClientOptions = builder.build();
        MongoClient mongoClient = new MongoClient(MongoHost,Integer.parseInt(MongoPort));

        return mongoClient;
    }


    /**
     * 注册mongodb操作类
     * @param mongoClient
     * @return
     */
    @Bean
    @ConditionalOnClass(MongoClient.class)
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        MongoTemplate mongoTemplate = new MongoTemplate(new SimpleMongoDbFactory(mongoClient, dbname));
        return mongoTemplate;
    }

}