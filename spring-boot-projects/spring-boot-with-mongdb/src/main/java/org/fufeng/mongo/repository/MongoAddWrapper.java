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

import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description 封装mongodb 添加操作
 * @create 2020-11-02
 */
@Component
public class MongoAddWrapper {
    private static final String COLLECTION_NAME = "fufeng";

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增一条记录
     */
    public void insert() {
        JSONObject object = new JSONObject();
        object.put("name", "fufeng");
        object.put("desc", "欢迎关注fufeng");
        object.put("age", 28);

        // 插入一条document
        mongoTemplate.insert(object, COLLECTION_NAME);


        JSONObject ans = mongoTemplate
                .findOne(new Query(Criteria.where("name").is("fufeng").and("age").is(28)), JSONObject.class,
                        COLLECTION_NAME);
        System.out.println(ans);
    }

    /**
     * 批量插入
     */
    public void insertMany() {
        List<Map<String, Object>> records = new ArrayList();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> record = new HashMap(4);
            record.put("wechart", "fufeng");
            record.put("blog", Arrays.asList("http://github.com/lcy2013", "http://github.com/lcy2013"));
            record.put("nums", 210);
            record.put("t_id", i);
            records.add(record);
        }

        // 批量插入文档
        mongoTemplate.insert(records, COLLECTION_NAME);

        // 查询插入的内容
        List<Map> result =
                mongoTemplate.find(new Query(Criteria.where("wechart").is("fufeng")), Map.class, COLLECTION_NAME);
        System.out.println("Query Insert Records: " + result);
    }


    /**
     * 数据不存在，通过 upsert 新插入一条数据
     *
     * set 表示修改key对应的value
     * addToSet 表示在数组中新增一条
     */
    public void upsertNoMatch() {
        // addToSet 表示将数据塞入document的一个数组成员中
        UpdateResult upResult = mongoTemplate.upsert(new Query(Criteria.where("name").is("fufeng").and("age").is(100)),
                new Update().set("age", 120).addToSet("add", "额外增加"), COLLECTION_NAME);
        System.out.println("nomatch upsert return: " + upResult);

        List<JSONObject> re = mongoTemplate
                .find(new Query(Criteria.where("name").is("fufeng").and("age").is(120)), JSONObject.class,
                        COLLECTION_NAME);
        System.out.println("after upsert return should not be null: " + re);
        System.out.println("------------------------------------------");
    }

    /**
     * 只有一条数据匹配，upsert 即表示更新
     */
    public void upsertOneMatch() {
        // 数据存在，使用更新
        UpdateResult result = mongoTemplate.upsert(new Query(Criteria.where("name").is("fufeng").and("age").is(120)),
                new Update().set("age", 100), COLLECTION_NAME);
        System.out.println("one match upsert return: " + result);

        List<JSONObject> ans = mongoTemplate
                .find(new Query(Criteria.where("name").is("fufeng").and("age").is(100)), JSONObject.class,
                        COLLECTION_NAME);
        System.out.println("after update return should be null: " + ans);
        System.out.println("------------------------------------------");
    }

    /**
     * 两条数据匹配时，upsert 将只会更新一条数据
     */
    public void upsertTwoMatch() {
        // 多条数据满足条件时，只会修改一条数据
        System.out.println("------------------------------------------");
        List<JSONObject> re = mongoTemplate
                .find(new Query(Criteria.where("name").is("fufeng").and("age").in(Arrays.asList(28, 100))),
                        JSONObject.class, COLLECTION_NAME);
        System.out.println("original record: " + re);

        UpdateResult result = mongoTemplate
                .upsert(new Query(Criteria.where("name").is("fufeng").and("age").in(Arrays.asList(28, 100))),
                        new Update().set("age", 120), COLLECTION_NAME);
        System.out.println("two match upsert return: " + result);

        re = mongoTemplate.find(new Query(Criteria.where("name").is("fufeng").and("age").is(120)), JSONObject.class,
                COLLECTION_NAME);
        System.out.println("after upsert return size should be 1: " + re);
        System.out.println("------------------------------------------");
    }

}

