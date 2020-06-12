# 分析需求语句记录
## 云搜分析语句
1. 查询总次数

2. 按日
db.getCollection('APP_DETAIL_VISIT_TABLE').aggregate(
[{ "$match" : { "USEDATE" : { "$gte" : "20180101000000", "$lte" : "20200101000000" } } }, 
{ "$project" : { "USEDATE" : 1, "day" : { "$substr" : ["$USEDATE", 0, 8] } } }, 
{ "$group" : { "_id" : "$day", "num" : { "$sum" : 1 } } }]
)

## 云分析语句


## 综合查询语句
