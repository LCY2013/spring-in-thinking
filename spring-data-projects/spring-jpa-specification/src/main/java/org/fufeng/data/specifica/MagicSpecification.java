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
package org.fufeng.data.specifica;

import org.fufeng.data.specifica.domain.Operator;
import org.fufeng.data.specifica.domain.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;


/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description 自定义 Specification 实现
 * @create 2020-10-23
 * @see Specification
 * @see Entity 泛型映射所有的实体领域模型类，实现动态查询不同实体模型
 */
public class MagicSpecification<Entity> implements Specification<Entity> {

    private static final long serialVersionUID = 8856176957986123651L;

    /**
     * 查询操作存储封装
     */
    private final SearchCriteria criteria;

    public MagicSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * 实现实体根据不同的字段、不同的Operator组合成不同的Predicate条件
     *
     * @param root             must not be {@literal null}.
     * @param query            must not be {@literal null}.
     * @param criteriaBuilder  must not be {@literal null}.
     * @return a {@link Predicate}, may be {@literal null}.
     */
    @Override
    public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (criteria.getOperation().compareTo(Operator.GT) == 0) {
            // 如果当前的 SearchCriteria 是大于操作
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString()
            );
        } else if (criteria.getOperation().compareTo(Operator.LT) == 0) {
            // 如果当前的 SearchCriteria 是小于操作
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString()
            );
        } else if (criteria.getOperation().compareTo(Operator.LK) == 0) {
            // 如果当前的 SearchCriteria 是等于操作
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%"
                );
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()),criteria.getValue().toString());
            }
        }
        return null;
    }

}
