package org.lcydream.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(WebServletConfiguration.class)
public class WebServletAutoConfiguration {
}
