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
import org.fufeng.data.springjpaoprator.dto.PersonInterfaceDTO;
import org.fufeng.data.springjpaoprator.dto.PersonOnlyName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @program: thinking-in-spring-boot
 * @description: 人员传输对象仓储
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-09-29
 */
public interface PersonDTORepository extends JpaRepository<Person,Long> {

    /**
     *  org.hibernate.query.criteria.internal.expression.function.ParameterizedFunctionExpression
     *
     * @param id 人员主键
     * @return 人员传输对象
     */
    @Query("select new org.fufeng.data.springjpaoprator.dto.PersonDTO(CONCAT(p.name,'-fufeng'),p.email,e.idCard) from Person p,PersonExtend e where p.id= e.userId and p.id=:id")
    PersonDTO findByPersonDTOId(@Param("id") Long id);

    //利用接口DTO获得返回结果，需要注意的是每个字段需要as和接口里面的get方法名字保持一样
    @Query("select CONCAT(p.name,'-magic') as name,UPPER(p.email) as email ,e.idCard as idCard from Person p,PersonExtend e where p.id= e.userId and p.id=:id")
    PersonInterfaceDTO findByPersonInterfaceDTOId(@Param("id") Long id);

    /**
     * 利用JQPl动态查询用户信息
     * @param name 人员名称
     * @param email 人员邮箱
     * @return PersonDTO接口
     */
    @Query("select p.name as name,p.email as email from Person p where (:name is null or p.name =:name) and (:email is null or p.email =:email)")
    PersonOnlyName findByPerson(@Param("name") String name, @Param("email") String email);
    /**
     * 利用原始sql动态查询用户信息
     * @param person
     * @return PersonDTO
     */
    @Query(value = "select p.name as name,p.email as email from Person p where (:#{#person.name} is null or p.name =:#{#person.name}) and (:#{#person.email} is null or p.email =:#{#person.email})",nativeQuery = true)
    PersonOnlyName findByPerson(@Param("person") Person person);

}
