/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-10-12
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

import org.assertj.core.util.Strings;
import org.fufeng.data.springjpaoprator.domain.relationship.many.Room;
import org.fufeng.data.springjpaoprator.domain.relationship.many.User;
import org.fufeng.data.springjpaoprator.domain.relationship.many.UserRoomRelation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring-boot
 * @description TODO
 * @create 2020-10-12
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRoomRelationRepositoryTest {

    @Autowired
    UserRoomRelationRepository userRoomRelationRepository;

    @Autowired
    ManyRoomRepository manyRoomRepository;

    @Autowired
    ManyUserRepository manyUserRepository;

    @BeforeAll
    @Rollback(false)
    @Transactional
    void init() {
        User user1 = User.builder().name("fufeng1").build();
        User user2 = User.builder().name("fufeng2").build();
        User user3 = User.builder().name("fufeng3").build();
        manyUserRepository.save(user1);
        manyUserRepository.save(user2);
        manyUserRepository.save(user3);

        Room room1 = Room.builder().title("room1").build();
        Room room2 = Room.builder().title("room2").build();
        Room room3 = Room.builder().title("room3").build();
        manyRoomRepository.save(room1);
        manyRoomRepository.save(room2);
        manyRoomRepository.save(room3);

        final UserRoomRelation userRoomRelation1 = UserRoomRelation.builder().room(room1).user(user1).build();
        final UserRoomRelation userRoomRelation2 = UserRoomRelation.builder().room(room1).user(user2).build();
        final UserRoomRelation userRoomRelation3 = UserRoomRelation.builder().room(room2).user(user3).build();
        final UserRoomRelation userRoomRelation4 = UserRoomRelation.builder().room(room3).user(user3).build();

        userRoomRelationRepository.save(userRoomRelation1);
        userRoomRelationRepository.save(userRoomRelation2);
        userRoomRelationRepository.save(userRoomRelation3);
        userRoomRelationRepository.save(userRoomRelation4);
    }

    @Test
    @Rollback(false)
    public void testUserRoomRelationships() {
        for(User user : manyUserRepository.findAll()){
            System.out.println(user+": "+user.getUserRoomRelations().stream()
                    .map(userRoomRelation -> userRoomRelation.getRoom()+":"+userRoomRelation.getUser())
                    .reduce((str1,str2)->str1+":"+str2).get());
        }
        System.out.println("===================");
        for (Room room : manyRoomRepository.findAll()){
            System.out.println(room+": "+room.getUserRoomRelations().stream()
                    .map(userRoomRelation -> userRoomRelation.getRoom()+":"+userRoomRelation.getUser())
                    .reduce((str1,str2)->str1+":"+str2).get());
        }
    }

}
