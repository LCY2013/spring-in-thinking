/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-18
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.bean.validation;

import org.fufeng.bean.validation.constrains.PrefixWith;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * @program: spring-in-thinking
 * @description: {@link PrefixWith} 前缀注解校验器
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-18
 * @see ConstraintValidator
 */
public class PrefixWithConstraintValidator implements ConstraintValidator<PrefixWith,String> {

    /**
     *  前缀字段
     */
    private String prefix;

    /**
     *  自定义消息
     */
    private String message;

    @Override
    public void initialize(PrefixWith constraintAnnotation) {
        this.prefix = constraintAnnotation.prefix();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)){
            return false;
        }
        if (!value.startsWith(this.prefix)){
            // 获取校验的字段信息
            final String validationMessage = Objects.isNull(this.message) || "".equals(this.message) ?"字段 必须以"+this.prefix+"开头":this.message;
            context.disableDefaultConstraintViolation();
            final ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder =
                    context.buildConstraintViolationWithTemplate(
                            validationMessage);
            constraintViolationBuilder.addConstraintViolation();
            return false;
        }
        return true;
    }

}
