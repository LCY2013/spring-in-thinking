package org.lcydream.data.binding.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @program: spring-in-thinking
 * @description: 学生
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-06-07 16:08
 */
@Data
@ToString
public class Student {
    private Long id;
    private String name;
    private String addr;
    private School school;
}
