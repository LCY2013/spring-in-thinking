package org.lcydream.beans.lifecycle.destruction;

import org.lcydream.beans.lifecycle.holder.UserHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

/**
 * @program: spring-in-thinking
 * @description: 销毁的BeanPostProcessor
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-06 15:23
 */
public class DestructionAwareBeanPostProcess implements DestructionAwareBeanPostProcessor {

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder", beanName) &&
                UserHolder.class.equals(bean.getClass())) {
            UserHolder userHolder = (UserHolder) bean;
            userHolder.setDescription("user holder v9");
            System.out.println(userHolder.getDescription());
        }
    }

}
