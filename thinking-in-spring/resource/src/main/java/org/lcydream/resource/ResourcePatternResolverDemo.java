package org.lcydream.resource;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.PathMatcher;
import org.lcydream.resource.utils.ResourcesUtils;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @program: spring-in-thinking
 * @description: 自定义 {@link ResourcePatternResolver} 实现
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-07 20:30
 * @see ResourcePatternResolver
 * @see PathMatchingResourcePatternResolver
 * @see PathMatcher
 */
public class ResourcePatternResolverDemo {

    public static void main(String[] args) throws IOException {
        //定义资源文件位置
        String javaFilePath = "/"+System.getProperty("user.dir")+"/thinking-in-spring/resource/src/main/java/org/lcydream/resource/";
        //定义PathMatcher
        String localPattern = javaFilePath + "*.java";
        //定义资源解析器
        PathMatchingResourcePatternResolver resourcePatternResolver =
                new PathMatchingResourcePatternResolver(new FileSystemResourceLoader());

        //设置PathMatcher
        resourcePatternResolver.setPathMatcher(new CustomPathMatcher());

        final Resource[] resources = resourcePatternResolver.getResources(localPattern);
        Stream.of(resources).map(ResourcesUtils::getContent).forEach(System.out::println);
    }

    static class CustomPathMatcher implements PathMatcher{
        @Override
        public boolean isPattern(String path) {
            return path.endsWith(".java");
        }

        @Override
        public boolean match(String pattern, String path) {
            return path.endsWith(".java");
        }

        @Override
        public boolean matchStart(String pattern, String path) {
            return false;
        }

        @Override
        public String extractPathWithinPattern(String pattern, String path) {
            return null;
        }

        @Override
        public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
            return null;
        }

        @Override
        public Comparator<String> getPatternComparator(String path) {
            return null;
        }

        @Override
        public String combine(String pattern1, String pattern2) {
            return null;
        }
    }

}
