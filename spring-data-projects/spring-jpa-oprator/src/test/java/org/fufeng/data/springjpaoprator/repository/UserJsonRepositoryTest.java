/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-10-13
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.data.springjpaoprator.repository;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.micrometer.core.instrument.util.StringUtils;
import org.assertj.core.util.Maps;
import org.fufeng.data.springjpaoprator.domain.json.UserJson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description {@link UserJsonRepository} 测试用例
 * @create 2020-10-13
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserJsonRepositoryTest {

    @Autowired
    UserJsonRepository userJsonRepository;

    @BeforeAll
    @Rollback(value = false)
    @Transactional
    void init(){
        final UserJson userJson = UserJson.builder().name("fufeng").createDate(Instant.now()).updateDate(new Date())
                .sex("nan").email("fufeng@magic.com").build();
        userJsonRepository.saveAndFlush(userJson);
    }

    /**
     *  UserJson 测试用例
     */
    @Test
    @Rollback(value = false)
    public void testUserJson() throws JsonProcessingException {
        final UserJson userJson = userJsonRepository.findById(1L).orElse(null);
        assert userJson != null;
        userJson.setOther(Maps.newHashMap("address","CD"));
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(userJson));
    }

    @Test
    @Rollback(false)
    public void testUserJsonModule() throws JsonProcessingException {
        UserJson userJson = userJsonRepository.findById(1L).get();
        userJson.setOther(Maps.newHashMap("address","CD"));
        //自定义 myInstant解析序列化和反序列化DateTimeFormatter.ISO_ZONED_DATE_TIME这种格式
        SimpleModule myInstant = new SimpleModule("instant", Version.unknownVersion())
                .addSerializer(java.time.Instant.class, new JsonSerializer<>() {
                    @Override
                    public void serialize(java.time.Instant instant,
                                          JsonGenerator jsonGenerator,
                                          SerializerProvider serializerProvider)
                            throws IOException {
                        if (instant == null) {
                            jsonGenerator.writeNull();
                        } else {
                            jsonGenerator.writeObject(instant.toString());
                        }
                    }
                })
                .addDeserializer(Instant.class, new JsonDeserializer<>() {
                    @Override
                    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        Instant result = null;
                        String text = jsonParser.getText();
                        if (!StringUtils.isEmpty(text)) {
                            result = ZonedDateTime.parse(text, DateTimeFormatter.ISO_ZONED_DATE_TIME).toInstant();
                        }
                        return result;
                    }
                });
        ObjectMapper objectMapper = new ObjectMapper();
        //注册自定义的module
        objectMapper.registerModule(myInstant);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userJson);
        System.out.println(json);
    }


}
