package com.founder;

import com.alibaba.fastjson.JSONArray;
import com.founder.model.AppDetailVisit;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
public class MongoUtil {
    private static MongoClient mongoClient;

    static {
        log.info("===============MongoDBUtil初始化========================");
        mongoClient = new MongoClient("192.168.1.112", 27017);
        MongoClientOptions.Builder options = new MongoClientOptions.Builder();
        options.connectionsPerHost(300);// 连接池设置为300个连接,默认为100
        options.connectTimeout(15000);// 连接超时，推荐>3000毫秒
        options.maxWaitTime(5000); //
        options.socketTimeout(0);// 套接字超时时间，0无限制
        // 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
        options.threadsAllowedToBlockForConnectionMultiplier(5000);
        options.writeConcern(WriteConcern.SAFE);
        options.build();
    }


    /**
     * 获取DB实例 - 指定DB
     *
     * @param dbName
     * @return
     */
    public static MongoDatabase getDB(String dbName) {
        if (dbName != null && !"".equals(dbName)) {
            MongoDatabase database = mongoClient.getDatabase(dbName);
            return database;
        }
        return null;
    }


    /**
     * 获取collection对象 - 指定Collection
     *
     * @param collName
     * @return
     */
    public static MongoCollection<Document> getCollection(String dbName,
                                                          String collName) {
        if (null == collName || "".equals(collName)) {
            return null;
        }
        if (null == dbName || "".equals(dbName)) {
            return null;
        }
        MongoCollection<Document> collection = mongoClient.getDatabase(dbName).getCollection(collName);
        return collection;
    }


    /**
     * 查询DB下的所有表名
     */
    public static List<String> getAllCollections(String dbName) {
        MongoIterable<String> colls = getDB(dbName).listCollectionNames();
        List<String> _list = new ArrayList<String>();
        for (String s : colls) {
            _list.add(s);
        }
        return _list;
    }


    /**
     * 获取所有数据库名称列表
     *
     * @return
     */
    public static MongoIterable<String> getAllDBNames() {
        MongoIterable<String> s = mongoClient.listDatabaseNames();
        return s;
    }


    /**
     * 删除一个数据库
     */
    public static void dropDB(String dbName) {
        getDB(dbName).drop();
    }


    /***
     * 删除文档
     *
     * @param dbName
     * @param collName
     */
    public static void dropCollection(String dbName, String collName) {
        getDB(dbName).getCollection(collName).drop();
    }


    /**
     * 查找对象 - 根据主键_id
     *
     * @param coll
     * @param id
     * @return
     */
    public static Document findById(MongoCollection<Document> coll, String id) {
        try {
            ObjectId _id = null;
            try {
                _id = new ObjectId(id);
            } catch (Exception e) {
                return null;
            }
            Document myDoc = coll.find(Filters.eq("_id", _id)).first();
            return myDoc;
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return null;

    }

    /***
     * 条件查询对象
     * @param coll
     * @param filter
     * @return
     */
    public static Document findByNames(MongoCollection<Document> coll, Bson filter) {
        try {
            return coll.find(filter).first();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return null;

    }

    /***
     * 多条件查询对象
     * @param coll
     * @param map
     * @return
     */
    public static Document findByNames(MongoCollection<Document> coll, Map<String, Object> map) {
        try {
            return coll.find(new BasicDBObject(map)).first();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return null;

    }

    /**
     * 统计数
     */
    public static int getCount(MongoCollection<Document> coll) {
        try {
            int count = (int) coll.count();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return 0;

    }

    /**
     * 查询 多个集合文档
     */
    public static MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
        try {
            return coll.find(filter).iterator();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return null;
    }

    /**
     * map集合 多条件查询
     *
     * @param coll
     * @param map
     * @return
     */
    public static MongoCursor<Document> find(MongoCollection<Document> coll, Map<String, Object> map) {
        try {
            return coll.find(new BasicDBObject(map)).iterator();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return null;

    }


    /***
     * 分页查询     默认按_id字段降序
     * @param coll
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static MongoCursor<Document> findByPage(MongoCollection<Document> coll, Map<String, Object> map, int pageNo, int pageSize) {
        try {
            Bson orderBy = new BasicDBObject("_id", -1);
            return coll.find(new BasicDBObject(map)).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return null;

    }

    /**
     * 分页查询 自定义排序
     *
     * @param coll
     * @param sorting
     * @param name
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static MongoCursor<Document> findByPage(MongoCollection<Document> coll, String sorting, String name,
                                                   Map<String, Object> map, int pageNo, int pageSize) {
        try {
            Bson orderBy = null;
            //降序
            if (sorting.equals("desc")) {
                orderBy = new BasicDBObject(name, -1);
            } else {
                orderBy = new BasicDBObject(name, 1);
            }
            return coll.find(new BasicDBObject(map)).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return null;

    }


    /**
     * 通过ID删除
     *
     * @param coll
     * @param id
     * @return
     */
    public static int deleteById(MongoCollection<Document> coll, String id) {
        try {
            int count = 0;
            ObjectId _id = null;
            _id = new ObjectId(id);
            Bson filter = Filters.eq("_id", _id);
            DeleteResult deleteResult = coll.deleteOne(filter);
            count = (int) deleteResult.getDeletedCount();
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return 0;
    }

    /**
     * 修改
     *
     * @param coll
     * @param id
     * @param newdoc
     * @return
     */
    public static Document updateById(MongoCollection<Document> coll, String id, Document newdoc) {
        ObjectId _idobj = null;
        try {
            _idobj = new ObjectId(id);
            Bson filter = Filters.eq("_id", _idobj);
            // coll.replaceOne(filter, newdoc); // 完全替代
            coll.updateOne(filter, new Document("$set", newdoc));
            return newdoc;
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
        return null;
    }

    /**
     * 添加
     *
     * @param coll
     * @param doc
     * @return
     */
    public static boolean save(MongoCollection<Document> coll, Document doc) {
        boolean falg = false;
        try {
            coll.insertOne(doc);
            falg = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("添加异常，异常信息：", e);
        } finally {
            close();
        }
        return falg;

    }

    /**
     * 关闭Mongodb
     */
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }


    @Test
    public void testInsert() {
        MongoCollection<Document> s_sf_sfsmsj = getCollection("GZDB_LOG", "APP_DETAIL_VISIT_TABLE");
        System.out.println("集合 s_sf_sfsmsj连接成功");

        AppDetailVisit appDetailVisit = new AppDetailVisit("", "查询标签来源", "", "查询成功", "123456", "20180114132018", "人员电子档案", "标签查询", "admin", "127.0.0.1", "admin", "广州市公安局");
        Document document = Document.parse(JSONArray.toJSON(appDetailVisit).toString());
        ArrayList<Document> documents = new ArrayList<>();
//        documents.add(document);
//        s_sf_sfsmsj.insertOne(document);
        boolean flag = MongoUtil.save(s_sf_sfsmsj, document);
        if (flag == true) {
            System.out.println("文档插入成功");
        } else {
            System.out.println("文档插入失败");
        }

    }
}
