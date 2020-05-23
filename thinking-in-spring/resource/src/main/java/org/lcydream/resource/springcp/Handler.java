package org.lcydream.resource.springcp;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @program: spring-in-thinking
 * @description: 扩展自定义的Java Resource Handler
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-23 21:47
 */
public class Handler extends sun.net.www.protocol.cp.Handler{

    //-Djava.protocol.handler.pkgs=org.lcydream.resource
    public static void main(String[] args) throws IOException {
        final URL url = new URL("springcp:///META-INF/prod.properties");
        final InputStream inputStream = url.openStream();
        System.out.println(StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8));
    }
}
