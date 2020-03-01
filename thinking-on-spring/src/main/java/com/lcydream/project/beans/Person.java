package com.lcydream.project.beans;

/**
 * 描述人的POJO
 *
 * Setter / Getter 方法
 * 可写（Writable） / 可读（readable）
 */
public class Person {

    //String to String
    String name; //properties

    //String to Integer
    Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
