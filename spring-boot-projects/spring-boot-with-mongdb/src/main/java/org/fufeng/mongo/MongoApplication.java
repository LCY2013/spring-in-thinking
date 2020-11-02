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
package org.fufeng.mongo;

import com.alibaba.fastjson.JSON;
import org.fufeng.mongo.repository.BasicRepository;
import org.fufeng.mongo.repository.MongoAddWrapper;
import org.fufeng.mongo.repository.MongoReadWrapper;
import org.fufeng.mongo.repository.MongoUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description spring boot 启动类
 * @create 2020-11-02
 */
@SpringBootApplication
public class MongoApplication {

    private final MongoReadWrapper mongoReadWrapper;
    private final MongoAddWrapper mongoAddWrapper;
    private final MongoUpdateWrapper mongoUpdateWrapper;

    public MongoApplication(MongoReadWrapper mongoReadWrapper, MongoAddWrapper mongoAddWrapper,
                       MongoUpdateWrapper mongoUpdateWrapper) {
        this.mongoReadWrapper = mongoReadWrapper;
        this.mongoAddWrapper = mongoAddWrapper;
        this.mongoUpdateWrapper = mongoUpdateWrapper;

        //addDemo();
        //updateDemo();
    }

    private void queryDemo() {
        // 指定查询
        this.mongoReadWrapper.specialFieldQuery();

        // 多条件查询
        this.mongoReadWrapper.andQuery();

        this.mongoReadWrapper.orQuery();

        this.mongoReadWrapper.inQuery();

        this.mongoReadWrapper.compareBigQuery();

        this.mongoReadWrapper.compareSmallQuery();

        this.mongoReadWrapper.regexQuery();

        this.mongoReadWrapper.countQuery();

        this.mongoReadWrapper.groupQuery();

        this.mongoReadWrapper.sortQuery();

        this.mongoReadWrapper.pageQuery();

        this.mongoReadWrapper.complexQuery();
    }

    private void addDemo() {
        this.mongoAddWrapper.insert();
        this.mongoAddWrapper.insertMany();
        this.mongoAddWrapper.upsertNoMatch();
        this.mongoAddWrapper.upsertOneMatch();
        this.mongoAddWrapper.upsertTwoMatch();
    }

    private void updateDemo() {
        this.mongoUpdateWrapper.basicUpdate();
        this.mongoUpdateWrapper.fieldUpdate();
        this.mongoUpdateWrapper.updateInnerArray();
        this.mongoUpdateWrapper.updateInnerDoc();
    }

    @Autowired
    private BasicRepository basicRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void init(){
//        Person person = new Person();
//        person.set_id("5f9fb2c213a7375d6a7c17a0");
//        person.setName("fufeng");
//        person.setUserId("17");
//        person.setAddress(Arrays.asList("成都","上海","深圳"));
        //final Person save = basicRepository.save(person);

        //BasicDBObject bson = new BasicDBObject();
        //bson.put("address","{'$in': ['成都']}");
        //mongoTemplate.getDb().getCollection("person").find(bson).forEach(JSON::toJSONString);

        //Query query = new Query(Criteria.where("address").in(Arrays.asList("成都","上海","深圳")));
        Query query = new Query(Criteria.where("address").in(new Object[]{"成都","上海","深圳"}));
        List<Map> map = mongoTemplate.find(query, Map.class, "person");
        System.out.println(JSON.toJSONString(map));

        Update update = new Update().addToSet("address", "4");
        mongoTemplate.updateFirst(query, update, "person");
        map = mongoTemplate.find(query, Map.class, "person");
        System.out.println(JSON.toJSONString(map));

        /*String pattern = String.format("^.{%s,}$", 1);
        Criteria criteria = Criteria.where("item_name").regex(pattern, "m");
        Query query = new Query(criteria);
        final Map map = mongoTemplate.findOne(query, Map.class, "fufeng");
        System.out.println(JSON.toJSONString(map));*/

    }

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class,args);
    }

}
