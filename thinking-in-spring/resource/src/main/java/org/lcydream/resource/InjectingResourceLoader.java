package org.lcydream.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * @program: spring-in-thinking
 * @description: ResourceLoader注入示例 {@link org.springframework.core.io.ResourceLoader}
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-23 20:49
 */
public class InjectingResourceLoader implements ResourceLoaderAware {

    //第一种方式:接口回调注入
    private ResourceLoader resourceLoader;

    //第二种方式:注解标注式
    @Autowired
    private ResourceLoader resourceLoaderByAutowired;

    //第三种方式:注解标注注入ApplicationContext
    @Autowired
    private AbstractApplicationContext applicationContext;

    @PostConstruct
    public void init(){
        System.out.println("resourceLoader == resourceLoaderByAutowired ? "
                + (resourceLoader == resourceLoaderByAutowired));
        System.out.println("resourceLoader == applicationContext ? "
                + (resourceLoader == applicationContext));
    }

    public static void main(String[] args) {
        //定义Application上下文
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        //注入配置类信息
        context.register(InjectingResourceLoader.class);
        //利用refresh启动应用上下文
        context.refresh();

        //关闭应用上下文
        context.close();
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
