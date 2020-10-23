/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-10-22
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

import org.assertj.core.util.Lists;
import org.fufeng.data.springjpaoprator.domain.jpaspecificationexecutor.SexEnum;
import org.fufeng.data.springjpaoprator.domain.jpaspecificationexecutor.User;
import org.fufeng.data.springjpaoprator.domain.jpaspecificationexecutor.UserAddress;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring-boot
 * @description {@link SpecificationUserRepository} 测试用例
 * @create 2020-10-22
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpecificationUserRepositoryTest {

    @Autowired
    private SpecificationUserRepository specificationUserRepository;

    @Autowired
    private SpecificationUserAddressRepository specificationUserAddressRepository;

    private Date now = new Date();

    @BeforeAll
    @Rollback(false)
    @Transactional
    void init() {
        User user = User.builder()
                .name("fufeng")
                .email("fufeng@magic.com")
                .sex(SexEnum.BOY)
                .age(20)
                .createDate(Instant.now())
                .updateDate(now)
                .build();
        specificationUserAddressRepository.saveAll(Lists.newArrayList(UserAddress.builder().user(user).address("cd").build(),
                UserAddress.builder().user(user).address("bj").build()));

    }

    @Test
    public void testSPE() {
        //模拟请求参数
        User userQuery = User.builder()
                .name("fufeng")
                .email("fufeng@magic.com")
                .sex(SexEnum.BOY)
                .age(19)
                .addresses(Lists.newArrayList(UserAddress.builder().address("cd").build()))
                .build();
        //假设的时间范围参数
        Instant beginCreateDate = Instant.now().plus(-2, ChronoUnit.HOURS);
        Instant endCreateDate = Instant.now().plus(1, ChronoUnit.HOURS);

        //利用Specification进行查询
        /*Page<User> users = specificationUserRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> ps = new ArrayList<>();
                if (StringUtils.isNotBlank(userQuery.getName())) {
                    //我们模仿一下like查询，根据name模糊查询
                    ps.add(cb.like(root.get("name"),"%" +userQuery.getName()+"%"));
                }

                if (userQuery.getSex()!=null){
                    //equal查询条件，这里需要注意，直接传递的是枚举
                    ps.add(cb.equal(root.get("sex"),userQuery.getSex()));
                }

                if (userQuery.getAge()!=null){
                    //greaterThan大于等于查询条件
                    ps.add(cb.greaterThan(root.get("age"),userQuery.getAge()));
                }

                if (beginCreateDate!=null&&endCreateDate!=null){
                    //根据时间区间去查询创建
                    ps.add(cb.between(root.get("createDate"),beginCreateDate,endCreateDate));
                }
                if (!ObjectUtils.isEmpty(userQuery.getAddresses())) {
                    //联表查询，利用root的join方法，根据关联关系表里面的字段进行查询。
                    ps.add(cb.in(root.join("addresses").get("address")).value(userQuery.getAddresses().stream().map(UserAddress::getAddress).collect(Collectors.toList())));
                }
                return query.where(ps.toArray(new Predicate[0])).getRestriction();
            }
        }, PageRequest.of(0, 2));*/

        Page<User> users = specificationUserRepository.findAll((Specification<User>) (root, query, cb) -> {
            List<Predicate> ps = new ArrayList<>();
            if (StringUtils.isNotBlank(userQuery.getName())) {
                //我们模仿一下like查询，根据name模糊查询
                ps.add(cb.like(root.get("name"),"%" +userQuery.getName()+"%"));
            }

            if (userQuery.getSex()!=null){
                //equal查询条件，这里需要注意，直接传递的是枚举
                ps.add(cb.equal(root.get("sex"),userQuery.getSex()));
            }

            if (userQuery.getAge()!=null){
                //greaterThan大于等于查询条件
                ps.add(cb.greaterThan(root.get("age"),userQuery.getAge()));
            }

            if (beginCreateDate!=null&&endCreateDate!=null){
                //根据时间区间去查询创建
                ps.add(cb.between(root.get("createDate"),beginCreateDate,endCreateDate));
            }
            if (!ObjectUtils.isEmpty(userQuery.getAddresses())) {
                //联表查询，利用root的join方法，根据关联关系表里面的字段进行查询。
                ps.add(cb.in(root.join("addresses").get("address")).value(userQuery.getAddresses().stream().map(UserAddress::getAddress).collect(Collectors.toList())));
            }
            return query.where(ps.toArray(new Predicate[0])).getRestriction();
        }, PageRequest.of(0, 2));
        users.stream().map(User::getName).forEach(System.out::println);
        //System.out.println(users);
    }

}
