package org.lcydream.bean.factory;

import org.lcydream.domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link User} Bean的 {@link FactoryBean} 实现
 */
public class UserFactoryBean implements FactoryBean<User> {

    @Override
    public User getObject() throws Exception {
        User user = new User();
        user.setId(Thread.currentThread().getId());
        user.setName("fufeng");
        return user;
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
