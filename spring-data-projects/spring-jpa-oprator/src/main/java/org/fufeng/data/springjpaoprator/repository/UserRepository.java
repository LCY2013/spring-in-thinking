/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-09-16
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

import org.fufeng.data.springjpaoprator.domain.sington.User;
import org.fufeng.data.springjpaoprator.dto.UserPojo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * @program: thinking-in-spring
 * @description: 领域对象 仓储
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-09-16
 */
public interface UserRepository extends JpaRepository<User,Long> {
//public interface UserRepository extends Repository<User,Long> {
//public interface UserRepository extends CrudRepository<User,Long> {
//public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    /**
     *  通过用户名称查询用户集合
     * @param name 用户名称
     * @return 用户集合
     */
    List<User> findByName(String name);

    /**
     *
     * @param name 名称
     * @param email 邮箱
     * @return 用户集合
     */
    List<User> findByNameAndEmail(String name,String email);

    /**
     *  自定义查询返回Stream信息
     * @param pageable 分页信息
     * @return Stream
     */
    @Query("select u from User u")
    Stream<User> findAllByCustomQueryAndStream(Pageable pageable);

    /**
     *  自定义查询返回Slice信息
     * @param pageable 分页信息
     * @return Slice
     */
    @Query("select u from User u")
    Slice<User> findAllByCustomQueryAndSlice(Pageable pageable);

    /**
     *  异步获取User 相关数据
     * @param name 用户名称
     * @return Future
     */
    @Async
    Future<User> getByName(String name);

    /**
     *  异步获取User 相关数据
     * @param name 用户名称
     * @return CompletableFuture
     */
    @Async
    CompletableFuture<User> getOneByName(String name);

    /**
     *  异步获取User 相关数据
     * @param email 邮箱地址
     * @return ListenableFuture
     */
    @Async
    ListenableFuture<User> getByEmail(String email);

    /**
     *  User 对象的pojo
     *
     * @param email 邮箱
     * @return User 对象的接口pojo
     */
    UserPojo getOneByEmail(String email);
}
