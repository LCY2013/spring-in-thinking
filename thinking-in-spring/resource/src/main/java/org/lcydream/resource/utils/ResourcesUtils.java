package org.lcydream.resource.utils;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

/**
 * @program: spring-in-thinking
 * @description: 资源工具
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-07 20:37
 */
public interface ResourcesUtils {

    static String getContent(Resource resource) {
        try {
            return getContent(resource,"UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String getContent(Resource resource,String encode) throws IOException {
        EncodedResource encodedResource = new EncodedResource(resource,encode);
        try(final Reader reader = encodedResource.getReader()) {
            //IO工具获取字符编码后的资源文件
            final String readerString = IOUtils.toString(reader);
            return readerString;
        }
    }
}
