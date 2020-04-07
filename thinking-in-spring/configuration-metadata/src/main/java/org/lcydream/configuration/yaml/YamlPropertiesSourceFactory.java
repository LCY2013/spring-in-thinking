package org.lcydream.configuration.yaml;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * @program: spring-in-thinking
 * @description: YAML 格式的 {@link org.springframework.core.io.support.PropertySourceFactory}
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-07 23:54
 */
public class YamlPropertiesSourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean =
                new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(resource.getResource());
        Properties factoryBeanObject = yamlPropertiesFactoryBean.getObject();
        return new PropertiesPropertySource(name,factoryBeanObject);
    }

}
