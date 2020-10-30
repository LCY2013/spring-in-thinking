/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-10-30
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.jpa;

import org.fufeng.jpa.auditor.UserAuditorAware;
import org.fufeng.jpa.common.SexEnum;
import org.fufeng.jpa.configration.AuditingConfiguration;
import org.fufeng.jpa.domain.Person;
import org.fufeng.jpa.domain.Student;
import org.fufeng.jpa.domain.User;
import org.fufeng.jpa.repository.PersonRepository;
import org.fufeng.jpa.repository.StudentRepository;
import org.fufeng.jpa.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description jpa 审计功能测试
 * @create 2020-10-30
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(AuditingConfiguration.class)
public class AuditorTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private StudentRepository studentRepository;

    @MockBean
    UserAuditorAware userAuditorAware;

    @Test
    public void testAuditor(){
        //由于测试用例模拟web context环境不是我们的重点，我们这里利用@MockBean，mock掉我们的方法，期待返回7这个用户ID
        Mockito.when(userAuditorAware.getCurrentAuditor()).thenReturn(Optional.of(7));
        //没有显式的指定更新时间、创建时间、更新人、创建人
        User user = User.builder()
                .name("fufeng")
                .email("fufeng@magic.com")
                .sex(SexEnum.BOY)
                .age(20)
                .build();
        userRepository.save(user);

        //验证是否有创建时间、更新时间，UserID是否正确；
        List<User> users = userRepository.findAll();
        Assertions.assertEquals(7,users.get(0).getCreateUserId());
        Assertions.assertNotNull(users.get(0).getLastModifiedTime());
        System.out.println(users.get(0));
    }

    @Test
    public void testAuditorPerson(){
        //由于测试用例模拟web context环境不是我们的重点，我们这里利用@MockBean，mock掉我们的方法，期待返回7这个用户ID
        Mockito.when(userAuditorAware.getCurrentAuditor()).thenReturn(Optional.of(7));
        //没有显式的指定更新时间、创建时间、更新人、创建人
        Person person = Person.builder()
                .name("fufeng")
                .email("fufeng@magic.com")
                .sex(SexEnum.BOY)
                .age(20)
                .build();
        personRepository.save(person);

        //验证是否有创建时间、更新时间，UserID是否正确；
        List<Person> persons = personRepository.findAll();
        Assertions.assertEquals(7,persons.get(0).getCreateUserId());
        Assertions.assertNotNull(persons.get(0).getLastModifiedTime());
        System.out.println(persons.get(0));
    }

    @Test
    public void testAuditorStudent(){
        //由于测试用例模拟web context环境不是我们的重点，我们这里利用@MockBean，mock掉我们的方法，期待返回7这个用户ID
        Mockito.when(userAuditorAware.getCurrentAuditor()).thenReturn(Optional.of(7));
        //没有显式的指定更新时间、创建时间、更新人、创建人
        Student student = Student.builder()
                .name("fufeng")
                .email("fufeng@magic.com")
                .sex(SexEnum.BOY)
                .age(20)
                .build();
        studentRepository.save(student);

        //验证是否有创建时间、更新时间，UserID是否正确；
        List<Student> students = studentRepository.findAll();
        Assertions.assertEquals(7,students.get(0).getCreateUserId());
        Assertions.assertNotNull(students.get(0).getLastModifiedTime());
        System.out.println(students.get(0));
    }
}
