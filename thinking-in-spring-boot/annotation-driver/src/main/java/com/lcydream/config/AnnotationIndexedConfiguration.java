package com.lcydream.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Indexed;

/**
 *  项目编译后会出现 classes/META-INF/spring.components
 *  这里用来定义预先的一些加载的Bean
 *      com.lcydream.Application=org.springframework.stereotype.Component
 *      com.lcydream.config.AnnotationIndexedConfiguration=org.springframework.stereotype.Component,com.lcydream.config.AnnotationIndexedConfiguration
 */
@Indexed
@Configuration
public class AnnotationIndexedConfiguration {
}
