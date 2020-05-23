package sun.net.www.protocol.cp;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @program: spring-in-thinking
 * @description: URLConnection实现 {@link URLConnection}
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-23 21:20
 */
public class CPURLConnection extends URLConnection{

    //定义类路径下的资源信息 类似与classpath://META-INF
    private final ClassPathResource resource;

    public CPURLConnection(URL url) {
        super(url);
        this.resource = new ClassPathResource(url.getPath());
    }

    @Override
    public void connect() throws IOException {

    }

    @Override
    public InputStream getInputStream() throws IOException {
        return resource.getInputStream();
    }
}
