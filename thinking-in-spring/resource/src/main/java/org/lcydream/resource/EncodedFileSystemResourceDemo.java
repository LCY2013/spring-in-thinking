package org.lcydream.resource;

import org.lcydream.resource.utils.ResourcesUtils;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;

/**
 * @program: spring-in-thinking
 * @description: #{description}
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-07 17:59
 */
public class EncodedFileSystemResourceDemo {

    public static void main(String[] args) throws IOException {
        String currentJavaFilePath = System.getProperty("user.dir")+"/thinking-in-spring/resource/src/main/java/org/lcydream/resource/EncodedFileSystemResourceDemo.java";
        //System.out.println(currentJavaFilePath);
        //FileSystemResource fileSystemResource = new FileSystemResource(currentJavaFilePath);
        File file = new File(currentJavaFilePath);
        //FileSystemResource -> WritableResource -> Resource
        FileSystemResource fileSystemResource = new FileSystemResource(file);
        //FileSystemResource -> EncodeResource
        System.out.println((ResourcesUtils.getContent(fileSystemResource)));
    }
}
