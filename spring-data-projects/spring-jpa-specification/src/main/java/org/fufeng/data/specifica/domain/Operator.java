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
package org.fufeng.data.specifica.domain;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description 自定义操作枚举
 * @create 2020-10-23
 */
public enum Operator {
    /**
     * 等于
     */
    EQ("="),
    /**
     * 等于
     */
    LK(":"),
    /**
     * 不等于
     */
    NE("!="),
    /**
     * 大于
     */
    GT(">"),
    /**
     * 小于
     */
    LT("<"),
    /**
     * 大于等于
     */
    GE(">=");
    Operator(String operator) {
        this.operator = operator;
    }
    private final String operator;

    /**
     *  获取操作
     * @param opt 具体的字符操作
     * @return {@link Operator}
     */
    public static Operator fromOperator(String opt){
        for (Operator operator : Operator.values()){
            if (operator.operator.equals(opt)){
                return operator;
            }
        }
        return EQ;
    }
}
