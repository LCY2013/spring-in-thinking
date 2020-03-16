package org.lcydream.domain;

import org.lcydream.enums.City;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 *  用户类
 */
public class User {

    private Long id;

    private String name;

    private City city;

    /**
     * 基于 {@link ClassPathResource} 加载配置文件
     */
    private Resource configFileLocal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Resource getConfigFileLocal() {
        return configFileLocal;
    }

    public void setConfigFileLocal(Resource configFileLocal) {
        this.configFileLocal = configFileLocal;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                ", configFileLocal=" + configFileLocal +
                '}';
    }

    public static User createUser(){
        User user = new User();
        user.setId(1L);
        user.setName("fufeng");
        user.setCity(City.CHENGDU);
        return user;
    }
}
