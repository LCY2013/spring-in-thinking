package org.lcydream.resource;

import org.lcydream.resource.utils.ResourcesUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @program: spring-in-thinking
 * @description: 依赖注入Spring Resource：Java注解场景注入Resource对象
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-23 17:45
 */
public class InjectingResourceDemo {

    @Value("classpath:/META-INF/default.properties")
    private Resource resource;

    @Value("classpath:/META-INF/*.properties")
    private Resource[] resources;

    @Value("classpath*:/META-INF/*.properties")
    private Resource[] resources_;

    @Value("classpath:/META-INF/*.properties")
    private List<Resource> resourcesList;

    @Value("classpath:/META-INF/*.properties")
    private Set<Resource> resourcesSet;

    //@Value("classpath:/META-INF/*.properties")
    //private Map<String,Resource> resourcesMap;

    @PostConstruct
    public void init(){
        System.out.println(ResourcesUtils.getContent(resource));

        System.out.println("------------>array starting");
        Stream.of(resources).map(ResourcesUtils::getContent).forEach(System.out::println);
        System.out.println("------------>array ended");

        System.out.println("------------>array starting");
        Stream.of(resources_).map(ResourcesUtils::getContent).
                filter(StringUtils::hasLength).forEach(System.out::println);
        System.out.println("------------>array ended");

        /*System.out.println("------------>list starting");
        resourcesList.stream().filter(Objects::nonNull).map(ResourcesUtils::getContent)
                .filter(StringUtils::hasText).forEach(System.out::println);
        System.out.println("------------>list ended");*/

        /*System.out.println("------------>set starting");
        resourcesSet.stream().filter(Objects::nonNull).map(ResourcesUtils::getContent)
                .filter(StringUtils::hasText).forEach(System.out::println);
        System.out.println("------------>set ended");*/

       /* System.out.println("------------>map starting");
        resourcesMap.values().stream().filter(Objects::nonNull).map(ResourcesUtils::getContent)
                .filter(StringUtils::hasText).forEach(System.out::println);
        System.out.println("------------>map ended");*/


    }

    public static void main(String[] args) {
        //生成一个基于注解的应用上下文
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        //注册配置Bean信息
        context.register(InjectingResourceDemo.class);
        //通过刷新启动应用上下文
        context.refresh();

        //停止应用上下文
        context.stop();
    }

}
