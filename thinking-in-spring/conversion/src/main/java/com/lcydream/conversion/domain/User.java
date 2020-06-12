package com.lcydream.conversion.domain;

import java.util.Properties;

/**
 * @program: spring-in-thinking
 * @description: 用户实体类
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-06-12 15:47
 */
public class User {

    private Integer id;

    private String name;

    private Properties context;

    private String contextAsText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Properties getContext() {
        return context;
    }

    public void setContext(Properties context) {
        this.context = context;
    }

    public String getContextAsText() {
        return contextAsText;
    }

    public void setContextAsText(String contextAsText) {
        this.contextAsText = contextAsText;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", context=" + context +
                ", contextAsText='" + contextAsText + '\'' +
                '}';
    }
}
