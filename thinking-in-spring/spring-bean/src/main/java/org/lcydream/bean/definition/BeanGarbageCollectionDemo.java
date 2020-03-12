package org.lcydream.bean.definition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Bean垃圾回收(GC)示例
 */
public class BeanGarbageCollectionDemo {

    public static void main(String[] args) throws InterruptedException {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注入容器的配置类信息(Configuration)
        applicationContext.register(BeanInitializationDemo.class);
        //启动容器
        applicationContext.refresh();
        //停止容器
        applicationContext.close();
        System.gc();
        TimeUnit.SECONDS.sleep(5);
    }

}
