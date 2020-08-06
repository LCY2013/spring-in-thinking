package org.lcydream;

import org.lcydream.configuration.WebServletConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  @see SpringBootApplication 说明不是一个限定标注于启动类上
 *  @see SpringBootApplication 等同于@EnableAutoConfiguration+@ComponentScan+@Configuration
 *  @see EnableAutoConfiguration 单独的该注解也能激活配置的Bean元素
 */
//@EnableAutoConfiguration
//@Configuration
//@ComponentScan
@SpringBootApplication(scanBasePackages = {"org.lcydream.configuration"}) //等同于@EnableAutoConfiguration+@ComponentScan+@Configuration
public class EmbedContainerApplication {

    public static void main(String[] args) {
        //SpringApplication.run(EmbedContainerApplication.class);
        SpringApplication.run(WebServletConfiguration.class);
    }

}
