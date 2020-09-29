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

import org.fufeng.data.springjpaoprator.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @program: thinking-in-spring-boot
 * @description: 测试UserDTO相关
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-09-29
 */
public interface UserDTORepository extends JpaRepository<User,Long> {

    /**
     *  通过query注解根据name查询用户实体信息
     * @param nameParam 名称
     * @return 用户实体
     */
    @Query("from User where name=:name")
    User findByName(@Param("name") String nameParam);

    @Query("select u from User u where u.email = ?1")
    User findByEmail(String emailAddress);

    @Query("select u from User u where u.name like %?1")
    List<User> findByNameEndsWith(String name);

    /**
     *  使用原始的sql语句
     * @param email 邮箱地址
     * @return 用户信息
     */
    @Query(value = "SELECT * FROM USERS WHERE email = ?1", nativeQuery = true)
    User findOneByEmail(String email);

    /*
    排序错误写法
    @Query(value = "select * from user where name=?1",nativeQuery = true)
    List<User> findByName(String name,Sort sort);
    */

    /**
     *  使用nativeQuery 方式调用
     *
     *  //调用的地方写法last_name是数据里面的字段名，不是对象的字段名
     *  repository.findByName("fufeng","name");
     * @param name 方法名称
     * @param sort 排序字段
     * @return 排序后的数据
     */
    @Query(value = "select * from user where name=?1 order by ?2",nativeQuery = true)
    List<User> findByName(String name,String sort);

    // 排序示例如下

    @Query("select u from User u where u.name like ?1%")
    List<User> findByAndSort(String name, Sort sort);

    @Query("select u.id, LENGTH(u.name) as fn_len from User u where u.name like ?1%")
    List<Object[]> findByAsArrayAndSort(String name, Sort sort);

    /*
    上面例子调用方式如下:
        repo.findByAndSort("lannister", new Sort("name"));
        repo.findByAndSort("stark", new Sort("LENGTH(name)"));
        repo.findByAndSort("targaryen", JpaSort.unsafe("LENGTH(name)"));
        repo.findByAsArrayAndSort("bolton", new Sort("fn_len"));
     */

    // 分页示例如下
    @Query(value = "select u from User u where u.name = ?1")
    Page<User> findByName(String lastname, Pageable pageable);

    /*
    上面例子调用方式如下:
        repository.findByName("fufeng",new PageRequest(1,10));
     */

    @Query(value = "select * from user where name=?1 /* #pageable# */",
            countQuery = "select count(*) from user where name=?1",
            nativeQuery = true)
    Page<User> findByNativeName(String name, Pageable pageable);

    /*
    上面例子调用方式如下:
        return userRepository.findByFirstName("fufeng",new PageRequest(1,10, Sort.Direction.DESC,"name"));
    //打印出来的sql
    select  *   from  user  where  name=? / #pageable# /  order by  name desc limit ?, ?
     */


    // @Param

    @Query("select u from User u where u.name = :name or u.email = :email")
    User findNameOrEmail(@Param("name") String name,
                                   @Param("email") String email);

    @Query("select u from User u where u.name = :name or u.email = :email")
    User findTop10ByNameOrEmail(@Param("name") String name,
                                        @Param("email") String email);

    // @Query 之 Projections 应用返回指定 DTO


}
