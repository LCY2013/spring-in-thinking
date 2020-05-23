package sun.net.www.protocol.cp;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @program: spring-in-thinking
 * @description: 测试自定义Java资源扩展
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-23 21:27
 */
public class CpHandlerTest {

    //JDK1.8 这种利用sun.net.www.protocol.${protocol}.Handler 可行
    //JDK11 后这种方式不可行，需要加上-Djava.protocol.handler.pkgs=package
    public static void main(String[] args) throws IOException {
        final URL url = new URL("cp:///META-INF/default.properties");
        final InputStream inputStream = url.openStream();
        System.out.println(StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8));
    }

}
