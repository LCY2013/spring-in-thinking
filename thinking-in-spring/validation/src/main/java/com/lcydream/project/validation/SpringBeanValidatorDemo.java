package com.lcydream.project.validation;

import lombok.Data;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @program: spring-in-thinking
 * @description: Spring Bean validator 校验整合
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-06-06 16:54
 */
public class SpringBeanValidatorDemo {

    public static void main(String[] args) {
        //生成一个Spring应用上下文
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:/META-INF/bean-validator-context.xml");

        //获取Spring容器中的校验器
//        Validator validator = context.getBean(Validator.class);
//        System.out.println(validator instanceof LocalValidatorFactoryBean);

        final ProcessStudent processStudent = context.getBean(ProcessStudent.class);
        final Student student = new Student();
        student.setId(1L);
        student.setName("L");
        processStudent.process(student);

        //关闭Spring应用上下文
        context.close();
    }

    @Component
    @Validated
    static class ProcessStudent{
        public void process(@Valid Student student){
            System.out.println(student);
        }
    }

    @Data
    static class Student{
        @NotNull
        private Long id;
        @NotNull
        @Max(3)
        @Min(2)
        private String name;
    }

}
