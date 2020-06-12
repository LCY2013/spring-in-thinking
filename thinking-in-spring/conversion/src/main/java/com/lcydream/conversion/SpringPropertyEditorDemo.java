package com.lcydream.conversion;

import com.lcydream.conversion.domain.User;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: spring-in-thinking
 * @description: spring {@link PropertyEditorRegistrar} 示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-06-12 15:50
 */
public class SpringPropertyEditorDemo {

    public static void main(String[] args) {
        //构建Spring应用上下文
        ConfigurableApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/property-editors-context.xml");

        // AbstractApplicationContext -> "conversionService" ConversionService Bean
        // -> ConfigurableBeanFactory#setConversionService(ConversionService)
        // -> AbstractAutowireCapableBeanFactory.instantiateBean
        // -> AbstractBeanFactory#getConversionService ->
        // BeanDefinition -> BeanWrapper -> 属性转换（数据来源：PropertyValues）->
        // setPropertyValues(PropertyValues) -> TypeConverter#convertIfNecessnary
        // TypeConverterDelegate#convertIfNecessnary  -> PropertyEditor or ConversionService

        //获取User信息
        final User user = applicationContext.getBean("user", User.class);
        System.out.println(user);

        //关闭Spring 应用上下文
        applicationContext.close();
    }

}
