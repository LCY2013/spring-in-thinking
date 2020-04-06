package org.lcydream.configuration.metadata;

import org.lcydream.domain.User;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @program: spring-in-thinking
 * @description: Bean 配置元信息示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-06 21:13
 */
public class BeanConfigurationMetadata {

    public static void main(String[] args) {
        /*//BeanDefinition定义
        BeanDefinition beanDefinition = new GenericBeanDefinition();
        //声明BeanDefinition
        //附加属性
        beanDefinition.setAttribute("name","magicLuo");*/

        //创建一个BeanDefinition的构造器
        final BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("name", "magic");
        //获取BeanDefinition
        final AbstractBeanDefinition beanDefinition =
                beanDefinitionBuilder.getBeanDefinition();
        //设置附加属性(不影响Bean的（populateBean）实例化，属性赋值，（initializeBean）初始化)
        beanDefinition.setAttribute("name", "magicLuo");
        //设置当前BeanDefinition赋值来源信息
        beanDefinition.setSource(BeanConfigurationMetadata.class);

        //创建一个BeanFactory
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        //注册一个BeanPostProcessor
        /*beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if("user".equals(beanName)
                    && User.class.equals(bean.getClass())){
                    BeanDefinition definition = beanFactory.getBeanDefinition(beanName);
                    if (BeanConfigurationMetadata.class.equals(definition.getSource())) {
                        final Object name = definition.getAttribute("name");
                        User user = (User) bean;
                        user.setName(String.valueOf(name));
                    }
                }
                return bean;
            }
        });*/
        //这三段等价于上面注释的部分，可以得出一个结论，BeanFactory#getBean是获取也是通过BeanDefinition创建
        //如果注册了BeanDefinition，其时需要在getBean的时候实例化，属性赋值以及初始化
        beanFactory.registerBeanDefinition("beanPostProcess",
                BeanDefinitionBuilder.genericBeanDefinition(BeanInitAfterPostProcessor.class).getBeanDefinition());
        beanFactory.addBeanPostProcessor(beanFactory.getBean("beanPostProcess", BeanInitAfterPostProcessor.class));

        //注册BeanDefinition信息
        beanFactory.registerBeanDefinition("user", beanDefinition);

        //开始依赖查找
        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);
    }

}
