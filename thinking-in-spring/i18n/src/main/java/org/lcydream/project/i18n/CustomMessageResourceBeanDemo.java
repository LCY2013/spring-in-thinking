package org.lcydream.project.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import static org.springframework.context.support.AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME;

/**
 * @program: spring-in-thinking
 * @description: Spring Boot 通过外部化配置简化 MessageSource Bean 构建
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-24 17:52
 * @see MessageSource
 * @see MessageSourceAutoConfiguration
 * @see ReloadableResourceBundleMessageSource
 */
@EnableAutoConfiguration
public class CustomMessageResourceBeanDemo {

    @Bean(MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource messageSource(){
        return new ReloadableResourceBundleMessageSource();
    }

    /**
     *  1、默认情况下spring AbstractApplicationContext#refresh()
     *                  -> AbstractApplicationContext#initMessageSource()
     *     这里初始化MessageSource时，如果没有自定义的MessageSource Bean，就会用默认的实现DelegatingMessageSource
     *  2、Springboot想要实现配置MessageSource的话，需要满足条件MessageSourceAutoConfiguration.ResourceBundleCondition
     *      即String basename = context.getEnvironment().getProperty("spring.messages.basename", "messages");
     *      上下文环境中存在默认名称为messages.properties配置文件
     *  3、springboot可以使用自定义的MessageResource去替换掉Springboot原有的配置实现，即存在一个名称为messageSource的MessageSource对象
     *      具体MessageSourceAutoConfiguration上注解@ConditionalOnMissingBean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, search = SearchStrategy.CURRENT)
     * @param args
     */
    public static void main(String[] args) {
        //Spring应用上下文
        final ConfigurableApplicationContext applicationContext =
                new SpringApplicationBuilder(CustomMessageResourceBeanDemo.class)
                .web(WebApplicationType.NONE)
                .run(args);

        //获取上下文中的BeanFactory
        final ConfigurableListableBeanFactory beanFactory =
                applicationContext.getBeanFactory();

        //查看IOC容器中是否存在messageSource Bean对象
        if(beanFactory.containsBean(MESSAGE_SOURCE_BEAN_NAME)){
            //查询MessageResource BeanDefinition
            System.out.println(beanFactory.getBeanDefinition(MESSAGE_SOURCE_BEAN_NAME));
            //查询MessageSource的对象
            final MessageSource messageSource =
                    applicationContext.getBean(MESSAGE_SOURCE_BEAN_NAME, MessageSource.class);
            System.out.println(messageSource);
        }

        //关闭Spring应用上下文
        applicationContext.close();
    }
}
