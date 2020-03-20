package org.lcydream.domain;

import org.lcydream.enums.City;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

/**
 *  用户类
 */
public class User implements BeanNameAware {

    private Long id;

    private String name;

    private City city;

    private City[] workCities;

    private List<City> lifeCities;

    /**
     * bean的原始名称
     */
    private String beanName;

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

    public City[] getWorkCities() {
        return workCities;
    }

    public void setWorkCities(City[] workCities) {
        this.workCities = workCities;
    }

    public List<City> getLifeCities() {
        return lifeCities;
    }

    public void setLifeCities(List<City> lifeCities) {
        this.lifeCities = lifeCities;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city=" + city +
                ", workCities=" + Arrays.toString(workCities) +
                ", lifeCities=" + lifeCities +
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

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @PostConstruct
    public void init(){
        System.out.println(this.beanName + " bean init...");
    }

    @PreDestroy
    public void destroy(){
        System.out.println(this.beanName + " bean destroy...");
    }
}
