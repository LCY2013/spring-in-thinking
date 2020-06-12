package com.lcydream.conversion;

import com.lcydream.conversion.domain.User;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

/**
 * @program: spring-in-thinking
 * @description: 自定义实现一个Spring 的{@link PropertyEditorRegistrar}
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-06-12 15:42
 * @see PropertyEditorRegistrar
 * @see PropertyEditorRegistry
 */
//将该类注册成一个Spring 管理的Bean实例
public class CustomerPropertyEditorRegistrar implements PropertyEditorRegistrar {

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        //1、通用类型转换
        //2、Java Bean 属性类型转换
        registry.registerCustomEditor(User.class,"context",new StringToPropertiesPropertyEditor());
    }
}
