package org.lcydream.configuration.metadata;

import org.lcydream.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @program: spring-in-thinking
 * @description: Bean 初始化完成后的回调
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-06 21:49
 */
public class BeanInitAfterPostProcessor implements  BeanFactoryAware,BeanPostProcessor {

    private BeanFactory beanFactory;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if("user".equals(beanName)
                && User.class.equals(bean.getClass())){
            if(beanFactory instanceof DefaultListableBeanFactory) {
                DefaultListableBeanFactory listableBeanFactory = (DefaultListableBeanFactory)beanFactory;
                BeanDefinition definition = listableBeanFactory.getBeanDefinition(beanName);
                if(BeanConfigurationMetadata.class.equals(definition.getSource())) {
                    final Object name = definition.getAttribute("name");
                    User user = (User) bean;
                    user.setName(String.valueOf(name));
                }
            }
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
