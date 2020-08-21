/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-08-21
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng;

import org.fufeng.config.PersonConfiguration;
import org.fufeng.domain.Person;
import org.fufeng.listener.PersonIntegrationListener;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * @program: spring-in-thinking
 * @description: TODO
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-21
 */
@RunWith(SpringRunner.class)
@ContextHierarchy(
        @ContextConfiguration(
             classes = {PersonConfiguration.class}
        )
)
@TestExecutionListeners(listeners = {
        PersonIntegrationListener.class,
        DependencyInjectionTestExecutionListener.class
})
@TestPropertySource(properties = {"name=lcy"})
public class PersonIntegrationTest {

    @Value("${name}")
    private String name;

    @Autowired
    private Person person;

    @BeforeClass
    public static void beforeClass(){
        System.err.println("before class");
    }

    @Before
    public void before(){
        System.err.println("before");
    }

    @Test
    public void testPrimaryPerson(){
        Assert.assertEquals(Integer.valueOf(18),this.person.getAge());
        Assert.assertEquals(Long.valueOf(7),this.person.getId());
        Assert.assertEquals("fufeng",this.person.getName());
    }

    @After
    public void after(){
        System.err.println("after");
    }

    @AfterClass
    public static void afterClass(){
        System.err.println("after calss");
    }
}
