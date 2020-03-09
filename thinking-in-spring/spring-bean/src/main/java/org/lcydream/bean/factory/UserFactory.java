package org.lcydream.bean.factory;

import org.lcydream.domain.User;

/**
 * {@link org.lcydream.domain.User} 抽象工厂
 * 默认实现{@link org.lcydream.bean.factory.cometure.DefaultUserFactory}
 */
public interface UserFactory {

    /**
     * 默认实现
     * @return User实例
     */
    default User createUser(){
        User user = new User();
        user.setId(24L);
        user.setName("fufeng");
        return user;
    }

    void initAnnotation();

    void annotationDestroy();
}
