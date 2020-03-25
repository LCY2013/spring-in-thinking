package org.lcydream.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ConditionalOnWebApplication
@Configuration
@Import(WebServletConfiguration.class)
public class WebServletAutoConfiguration {
}
