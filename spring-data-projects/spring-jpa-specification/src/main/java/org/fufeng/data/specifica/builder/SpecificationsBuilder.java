/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-10-23
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.data.specifica.builder;

import org.fufeng.data.specifica.MagicSpecification;
import org.fufeng.data.specifica.domain.Operator;
import org.fufeng.data.specifica.domain.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description  Specification 构建器，通过用户请求的查询参数去构建Specification
 * @create 2020-10-23
 * @see Specification
 * @see Entity 泛型映射所有的实体领域模型类，实现动态查询不同实体模型
 */
public class SpecificationsBuilder<Entity> {

    /**
     * 查询参数封装的SearchCriteria 操作信息
     */
    private final List<SearchCriteria> params;

    public SpecificationsBuilder() {
        //初始化params，保证每次实例都是一个新的ArrayList
        this.params = new ArrayList<>();
    }

    //利用正则表达式取我们search参数里面的值，解析成SearchCriteria对象
    public Specification<Entity> buildSpecification(String search) {
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            this.with(matcher.group(1), Operator.fromOperator(matcher.group(2)), matcher.group(3));
        }
        return this.build();
    }
    //根据参数返回我们刚才创建的SearchCriteria
    private SpecificationsBuilder<Entity> with(String key, Operator operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }
    //根据我们刚才创建的MagicSpecification返回所需要的Specification
    private Specification<Entity> build() {
        if (params.size() == 0) {
            return null;
        }
        List<Specification<Entity>> specs = params.stream()
                .map(MagicSpecification<Entity>::new)
                .collect(Collectors.toList());
        Specification<Entity> result = specs.get(0);
        for (int i = 1; i < params.size(); i++) {
            result = Objects.requireNonNull(Specification.where(result))
                    .and(specs.get(i));
        }
        return result;
    }
}
