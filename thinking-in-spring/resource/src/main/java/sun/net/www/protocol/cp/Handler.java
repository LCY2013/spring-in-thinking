package sun.net.www.protocol.cp;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * @program: spring-in-thinking
 * @description: 扩展Java资源信息 {@link URLStreamHandler} cp协议
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-23 21:20
 */
public class Handler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        return new CPURLConnection(u);
    }

}
