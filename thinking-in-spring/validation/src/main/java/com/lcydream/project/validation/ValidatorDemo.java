package com.lcydream.project.validation;

import org.lcydream.domain.User;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Locale;
import java.util.Objects;

/**
 * @program: spring-in-thinking
 * @description: 自定义Spring {@link Validator} 实现
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-06-02 21:31
 */
public class ValidatorDemo {

    public static void main(String[] args) {
        //1、创建一个字段都为空的User对象
        User user = new User();
        //2、创建自己定义的对象校验器
        Validator validator = new UserValidator();
        System.out.println("用户是否支持Validator校验:"+validator.supports(user.getClass()));
        //3、创建Errors对象
        Errors errors = new BeanPropertyBindingResult(user,"user");
        //4、开始验证
        validator.validate(user,errors);
        //5、创建一个MessageSource对象,用于输出校验信息
        MessageSource messageSource = createMessageSource();
        //输出错误文案信息
        errors.getAllErrors().stream().map(objectError
                -> messageSource.getMessage(Objects.requireNonNull(objectError.getCode()),
                        objectError.getArguments(),Locale.getDefault()))
                        .forEach(System.out::println);
    }

    /**
     *  创建一个消息资源实现
     * @return
     */
    private static MessageSource createMessageSource() {
        StaticMessageSource messageSource = new StaticMessageSource();
        messageSource.addMessage("id should not null", Locale.getDefault(),"用户ID不能为空");
        messageSource.addMessage("name should not null",Locale.getDefault(),"用户名称不能为空");
        messageSource.addMessage("name's length illegal",Locale.getDefault(),"用户长度不合法");
        return messageSource;
    }

    /**
     * 定义User对象的校验器
     */
    static class UserValidator implements Validator {
        @Override
        public boolean supports(Class<?> clazz) {
            //满足User对象的就进行校验
            return User.class.isAssignableFrom(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            //这个校验器只支持User，所以这里直接给转User就行
            User user = (User) target;
            //进行校验逻辑
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"id","id should not null");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","name should not null");
            //判断name长度是否合法
            if(StringUtils.isEmpty(user.getName())
                    || user.getName().length() < 10){
                errors.rejectValue("name","name's length illegal");
            }
        }
    }
}
