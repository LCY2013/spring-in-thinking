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

}
