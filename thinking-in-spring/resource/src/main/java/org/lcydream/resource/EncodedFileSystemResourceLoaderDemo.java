package org.lcydream.resource;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

/**
 * @program: spring-in-thinking
 * @description: {@link org.springframework.core.io.FileSystemResourceLoader}
 *      通过FileSystemResourceLoader加载资源文件
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-07 17:59
 */
public class EncodedFileSystemResourceLoaderDemo {

    public static void main(String[] args) throws IOException {
        String currentJavaFilePath = "/"+System.getProperty("user.dir")+"/thinking-in-spring/resource/src/main/java/org/lcydream/resource/EncodedFileSystemResourceLoaderDemo.java";
        FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
        Resource resource = fileSystemResourceLoader.getResource(currentJavaFilePath);
        EncodedResource encodedResource = new EncodedResource(resource);

        try(final Reader reader = encodedResource.getReader()) {
            //IO工具获取字符编码后的资源文件
            final String readerString = IOUtils.toString(reader);
            System.out.println(readerString);
        }
    }
}
