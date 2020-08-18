/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-18
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.fufeng.serializer.ObjectSerializer;
import org.junit.Test;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @program: spring-in-thinking
 * @description: {@link Producer} kafka生产者使用
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-18
 */
public class KafkaProducerTest {

    /**
     *  发送kafka消息 测试
     */
    @Test
    public void sendMessage() throws ExecutionException, InterruptedException {
        // 定义kafka生产者配置信息
        final Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094");
        // properties.put("key.serializer", StringSerializer.class);
        // properties.put("value.serializer", StringSerializer.class);
        properties.put("key.serializer", ObjectSerializer.class);
        properties.put("value.serializer", ObjectSerializer.class);
        // kafka 生产者
        final KafkaProducer<String,String> kafkaProducer = new KafkaProducer<>(properties);
        // 定义一个生产者消息
        final ProducerRecord<String,String> record =
                new ProducerRecord<>("sbwk","name","fufeng");
        // 发送kafka消息
        final Future<RecordMetadata> future = kafkaProducer.send(record);
        // 通过future获取数据
        final RecordMetadata recordMetadata = future.get();
        System.out.println(recordMetadata);
    }

}
