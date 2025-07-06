package org.fufeng.reactive.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@RestController
public class Jdk8Controller {
    public static void main(String[] args) throws NoSuchMethodException {
//        beforeJdk8();
        afterJdk8();
    }

    private static void afterJdk8() throws NoSuchMethodException {
        Method method = Jdk8Controller.class.getMethod("getPrice", String.class, String.class);
        for (Parameter p: method.getParameters()){
            System.out.println(p.getType() + "/"+p.getName());
        }
    }

    private static void beforeJdk8() throws NoSuchMethodException {
        Method method = Jdk8Controller.class.getMethod("getPrice", String.class, String.class);
        for (Class clazz : method.getParameterTypes()) {
            System.out.println(clazz);
        }
    }

    @RequestMapping("/{p1}/{p2}")
    public String getPrice(
            @PathVariable("p1") String path1,
            @PathVariable("p2") String path2
    ) {
        return null;
    }
}
