package org.lcydream.bean.definition;

import org.lcydream.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * {@link org.springframework.beans.factory.config.BeanDefinition} 构建示例
 *  第一种: {@link org.springframework.beans.factory.support.BeanDefinitionBuilder}
 *  第二种: {@link org.springframework.beans.factory.support.AbstractBeanDefinition} 以及它的实现子类
 */
public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {
        //第一种：通过BeanDefinitionBuilder 构建Bean
        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(User.class);
        //通过属性给User实例添加属性值
        beanDefinitionBuilder.addPropertyValue("id",24L)
                .addPropertyValue("name","fufeng");
        //获取BeanDefinition 实例
        AbstractBeanDefinition beanDefinition =
                beanDefinitionBuilder.getBeanDefinition();
        //BeanDefinition不是最终的Bean实例，它的定义可以修改

        //第二种：通过AbstractBeanDefinition子类进行创建
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        //定义Bean的基础类型
        genericBeanDefinition.setBeanClass(User.class);
        //通过MutablePropertyValues进行属性的批量操作
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("id",24L)
                .add("name","fufeng");
        //propertyValues.addPropertyValue("id",24L);
        //propertyValues.addPropertyValue("name","fufeng");
        //设置批量操作的属性值
        genericBeanDefinition.setPropertyValues(propertyValues);

    }

}
