package org.fufeng.sca.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author luocy
 * @description 自定义配置
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 * @see RefreshScope 动态刷新的具体注解实现
 */
@Data
@Configuration
@RefreshScope
public class CustomConfig {

    @Value("${custom.flag}")
    private String flag;

    @Value("${custom.database}")
    private String database;

    @Value("${one.name}")
    private String one;

    @Value("${two.name}")
    private String two;

    @Value("${three.name}")
    private String three;

    @Override
    public String toString() {
        return "CustomConfig{" +
                "flag='" + flag + '\'' +
                ", database='" + database + '\'' +
                ", one='" + one + '\'' +
                ", two='" + two + '\'' +
                ", three='" + three + '\'' +
                '}';
    }
}
