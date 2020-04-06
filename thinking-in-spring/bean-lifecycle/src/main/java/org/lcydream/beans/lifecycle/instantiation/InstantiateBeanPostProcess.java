package org.lcydream.beans.lifecycle.instantiation;

import org.lcydream.beans.lifecycle.holder.UserHolder;
import org.lcydream.domain.SuperUser;
import org.lcydream.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

import java.util.Iterator;

public class InstantiateBeanPostProcess implements InstantiationAwareBeanPostProcessor {

    /**
     * 这里可以做一些代理类替换原生类
     *   实例化过程
     * @param beanClass 目标类字节码
     * @param beanName  bean名称
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("superUser", beanName) &&
                SuperUser.class.equals(beanClass)) {
            //把配置文件中的SuperUser替换掉
            return new SuperUser();
        }
        //为null的时候就是用默认配置文件中的
        return null;
    }

    /**
     * @param bean     bean
     * @param beanName beanName
     * @return @return {@code true} if properties should be set on the bean;
     * {@code false} if property population should be skipped.
     * Normal implementations should return {@code true}.
     *
     * 实例化过程
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("user", beanName) &&
                User.class.equals(bean.getClass())) {
            User user = (User) bean;
            user.setId(25L);
            user.setName("MagicLuo");
            //user 对象不允许填入配置好的属性值(配置信息 -> 属性值)
            return false;
        }
        return true;
    }

    /**
     * 如果postProcessAfterInstantiation返回的false这里将不会被调用
     * user 取消掉了属性设置，那么这里就会取消执行
     * superUser 用了自定义创建，不会走下面的实例化流程，也会取消这里的执行
     * userHolder 可以使用这里
     *
     *  实例化过程
     *
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        //对UserHolder进行拦截
        if (ObjectUtils.nullSafeEquals("userHolder", beanName) &&
                UserHolder.class.equals(bean.getClass())) {
            //<property name="number" value="2"/> 等价于 propertyValues.add("number","1")
            MutablePropertyValues propertyValues = new MutablePropertyValues();
            Iterator<PropertyValue> iterator = pvs.iterator();
            for (; iterator.hasNext(); ) {
                PropertyValue pv = iterator.next();
                propertyValues.addPropertyValue(pv);
            }
            propertyValues.add("number", "1");
            propertyValues.add("description", "user holder v2");
            return propertyValues;
        }
        return null;
    }

    /**
     * 初始化前调用
     *  初始化过程
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder", beanName) &&
                UserHolder.class.equals(bean.getClass())) {
            UserHolder userHolder = (UserHolder) bean;
            userHolder.setDescription("user holder v3");
        }
        return bean;
    }

    /**
     * 初始化过程
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder", beanName) &&
                UserHolder.class.equals(bean.getClass())) {
            UserHolder userHolder = (UserHolder) bean;
            userHolder.setDescription("user holder v7");
        }
        return null;
    }
}