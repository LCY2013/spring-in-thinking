/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-09-29
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

import org.fufeng.data.springjpaoprator.domain.Person;
import org.fufeng.data.springjpaoprator.dto.PersonDTO;
import org.fufeng.data.springjpaoprator.domain.PersonExtend;
import org.fufeng.data.springjpaoprator.dto.PersonInterfaceDTO;
import org.fufeng.data.springjpaoprator.dto.PersonOnlyName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

/**
 * @program: thinking-in-spring-boot
 * @description: PersonRepository 测试用例
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-09-29
 */
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonExtendRepository personExtendRepository;

    @Autowired
    PersonDTORepository personDTORepository;

    @Test
    public void personRepositoryTest(){
        //新增一条用户数据
        personRepository.save(Person.builder().name("fufeng").email("fufeng@magic.com").sex("man").address("chengdu").build());
        //再新增一条和用户一对一的UserExtend数据
        personExtendRepository.save(PersonExtend.builder().userId(1L).idCard("2748984934").ages(18).studentNumber("75").build());
        //查询我们想要的结果
        List<Object[]> personArray = personRepository.findByUserId(1L);
        System.out.println(String.valueOf(personArray.get(0)[0])+String.valueOf(personArray.get(0)[1]));
    }

    @Test
    public void testQueryAnnotationDto() {
        personRepository.save(Person.builder().name("fufeng").email("fufeng@magic.com").sex("man").address("chengdu").build());
        personExtendRepository.save(PersonExtend.builder().userId(1L).idCard("3937432356023333353").ages(18).studentNumber("75").build());
        PersonDTO personDTO = personDTORepository.findByPersonDTOId(1L);
        System.out.println(personDTO);
    }

    @Test
    public void testQueryAnnotationInterfaceDto() {
        personRepository.save(Person.builder().name("fufeng").email("fufeng@magic.com").sex("man").address("chengdu").build());
        personExtendRepository.save(PersonExtend.builder().userId(1L).idCard("3937432356023333353").ages(18).studentNumber("75").build());
        PersonInterfaceDTO personInterfaceDTO = personDTORepository.findByPersonInterfaceDTOId(1L);
        System.out.println(personInterfaceDTO.getName()+":"+personInterfaceDTO.getEmail()+":"+personInterfaceDTO.getIdCard());
    }

    @Test
    public void testQueryDynamicDto() {
        personRepository.save(Person.builder().name("fufeng").email("fufeng@magic.com").sex("man").address("chengdu").build());
        personExtendRepository.save(PersonExtend.builder().userId(1L).idCard("3937432356023333353").ages(18).studentNumber("75").build());
        final PersonOnlyName personOnlyName = personDTORepository.findByPerson(Person.builder().name("fufeng").build());
        System.out.println(personOnlyName.getName() + ":" + personOnlyName.getEmail());

        final PersonOnlyName personName = personDTORepository.findByPerson("fufeng", "fufeng@magic.com");
        System.out.println(personName.getName() + ":" + personName.getEmail());
    }

}
