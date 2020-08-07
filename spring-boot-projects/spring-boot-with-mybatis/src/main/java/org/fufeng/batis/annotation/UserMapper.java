/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-07
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.batis.annotation;

import org.apache.ibatis.annotations.*;
import org.fufeng.batis.entity.User;
import org.fufeng.batis.handler.DescriptionTypeHandler;

/**
 * @program: spring-in-thinking
 * @description: 通过注解标注实现mybatis
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-07
 */
@Mapper
public interface UserMapper {

    @Results(value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "name",column = "name"),
            @Result(property = "age",column = "age"),
            @Result(property = "description",column = "desc",typeHandler = DescriptionTypeHandler.class),
            @Result(property = "height",column = "height")
    })
    @Select("select id,name,age,`desc`,height from user where id = #{id}")
    User selectUser(@Param("id")int id);
}
