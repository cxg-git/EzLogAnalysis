package com.founder;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoTest {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("192.168.1.112", 27017);
        MongoDatabase gzdb = mongoClient.getDatabase("gzdb");
        System.out.println("Connect to database successfully");
    }

}
