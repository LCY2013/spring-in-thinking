package org.lcydream.configuration.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @program: spring-in-thinking
 * @description: user.xsd NamespaceHandler 处理器实现
 * @see {@link org.springframework.beans.factory.xml.NamespaceHandler}
 * @see {@link NamespaceHandlerSupport}
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-07 21:32
 */
public class UsersNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        //注册user元素对应的BeanDefinitionParser实现
        registerBeanDefinitionParser("user",new UserBeanDefinitionParser());
    }

}
