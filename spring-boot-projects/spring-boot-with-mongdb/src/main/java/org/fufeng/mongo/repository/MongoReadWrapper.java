/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-11-02
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.mongo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description mongo db 读操作
 * @create 2020-11-02
 */
@Component
public class MongoReadWrapper {
    private static final String COLLECTION_NAME = "fufeng";

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 指定field查询
     */
    public void specialFieldQuery() {
        Query query = new Query(Criteria.where("user").is("fufeng"));
        // 查询一条满足条件的数据
        Map result = mongoTemplate.findOne(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | specialFieldQueryOne: " + result);

        // 满足所有条件的数据
        List<Map> ans = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | specialFieldQueryAll: " + ans);
    }

    /**
     * 多个查询条件同时满足
     */
    public void andQuery() {
        Query query = new Query(Criteria.where("user").is("fufeng").and("age").is(18));
        Map result = mongoTemplate.findOne(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | andQuery: " + result);
    }

    /**
     * 或查询
     */
    public void orQuery() {
        // 等同于 ﻿db.getCollection('demo').find({"user": "fufeng", $or: [{ "age": 18}, { "sign": {$exists: true}}]})
        Query query = new Query(Criteria.where("user").is("fufeng")
                .orOperator(Criteria.where("age").is(18), Criteria.where("sign").exists(true)));
        List<Map> result = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | orQuery: " + result);

        // 单独的or查询
        // 等同于Query: { "$or" : [{ "age" : 18 }, { "sign" : { "$exists" : true } }] }, Fields: { }, Sort: { }
        query = new Query(new Criteria().orOperator(Criteria.where("age").is(18), Criteria.where("sign").exists(true)));
        result = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | orQuery: " + result);
    }

    /**
     * in查询
     */
    public void inQuery() {
        // 相当于:
        Query query = new Query(Criteria.where("age").in(Arrays.asList(18, 20, 30)));
        List<Map> result = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | inQuery: " + result);
    }

    /**
     * 数字类型，比较查询 >
     */
    public void compareBigQuery() {
        // age > 18
        Query query = new Query(Criteria.where("age").gt(18));
        List<Map> result = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | compareBigQuery: " + result);

        // age >= 18
        query = new Query(Criteria.where("age").gte(18));
        result = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | compareBigQuery: " + result);
    }

    /**
     * 数字类型，比较查询 <
     */
    public void compareSmallQuery() {
        // age < 20
        Query query = new Query(Criteria.where("age").lt(20));
        List<Map> result = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | compareSmallQuery: " + result);

        // age <= 20
        query = new Query(Criteria.where("age").lte(20));
        result = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | compareSmallQuery: " + result);
    }

    /**
     * 正则查询
     */
    public void regexQuery() {
        Query query = new Query(Criteria.where("user").regex("^fufeng"));
        List<Map> result = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | regexQuery: " + result);
    }

    /**
     * 查询总数
     */
    public void countQuery() {
        Query query = new Query(Criteria.where("user").is("fufeng"));
        long cnt = mongoTemplate.count(query, COLLECTION_NAME);
        System.out.println("query: " + query + " | cnt " + cnt);
    }

    /**
     * 分组查询
     */
    public void groupQuery() {
        // 根据用户名进行分组统计，每个用户名对应的数量
        // aggregate([ { "$group" : { "_id" : "user" , "userCount" : { "$sum" : 1}}}] )
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("user").count().as("userCount"));
        AggregationResults<Map> ans = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        System.out.println("query: " + aggregation + " | groupQuery " + ans.getMappedResults());
    }

    /**
     * 排序查询
     */
    public void sortQuery() {
        // sort查询条件，需要用with来衔接
        Query query = Query.query(Criteria.where("user").is("fufeng")).with(Sort.by("age"));
        List<Map> result = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | sortQuery " + result);
    }

    /**
     * 分页查询
     */
    public void pageQuery() {
        // limit限定查询2条
        Query query = Query.query(Criteria.where("user").is("fufeng")).with(Sort.by("age")).limit(2);
        List<Map> result = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | limitPageQuery " + result);


        // skip()方法来跳过指定数量的数据
        query = Query.query(Criteria.where("user").is("fufeng")).with(Sort.by("age")).skip(2);
        result = mongoTemplate.find(query, Map.class, COLLECTION_NAME);
        System.out.println("query: " + query + " | skipPageQuery " + result);
    }

    public void complexQuery() {

    }
}
