package com.lcydream.project.validation;

import org.lcydream.domain.User;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @program: spring-in-thinking
 * @description: 错误文案示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-26 21:44
 */
public class ErrorsMessageDemo {

    public static void main(String[] args) {
        //1、创建User对象
        User user = new User();
        //2、选择Errors ->  BeanPropertyBindingResult
        Errors errors = new BeanPropertyBindingResult(user,"user");
        //3、选择调用reject 或者 rejectValue
        // reject 生成 ObjectError
        // reject 生成 FieldError
        errors.reject("id should not null");
        // rejectValue ->  user.name == user.getName()
        errors.rejectValue("name","name should not null");
        // 4、获取 Errors 中 ObjectError 和 FieldError
        // FieldError is ObjectError
        final List<ObjectError> allErrors = errors.getAllErrors();
        final List<FieldError> fieldError = errors.getFieldErrors();
        final List<ObjectError> globalError = errors.getGlobalErrors();
        // 5、 通过 ObjectError 和 FieldError 中的 code 和 args 来关联 MessageSource 实现
        MessageSource messageSource = createMessageSource();

        System.out.println("---------allErrors--------");
        allErrors.stream()
                .map(error ->
                        messageSource.getMessage(Objects.requireNonNull(error.getCode())
                                ,error.getArguments()
                                ,Locale.getDefault()))
                .forEach(System.out::println);
        System.out.println("---------fieldError--------");
        fieldError.stream()
                .map(error ->
                        messageSource.getMessage(Objects.requireNonNull(error.getCode())
                                ,error.getArguments()
                                ,Locale.getDefault()))
                .forEach(System.out::println);
        System.out.println("---------globalError--------");
        globalError.stream()
                .map(error ->
                        messageSource.getMessage(Objects.requireNonNull(error.getCode())
                                ,error.getArguments()
                                ,Locale.getDefault()))
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
        return messageSource;
    }


}
