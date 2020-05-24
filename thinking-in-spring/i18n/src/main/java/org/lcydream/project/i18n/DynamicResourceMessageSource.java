package org.lcydream.project.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.*;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

/**
 * @program: spring-in-thinking
 * @description:
 * 动态（更新）资源 {@link MessageSource} 实现
 * <p>
 * 实现步骤：
 * <p>
 * 1. 定位资源位置（ Properties 文件）
 * 2. 初始化 Properties 对象
 * 3. 实现 AbstractMessageSource#resolveCode 方法
 * 4. 监听资源文件（Java NIO 2 WatchService）
 * 5. 使用线程池处理文件变化
 * 6. 重新装载 Properties 对象
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-05-24 20:56
 */
public class DynamicResourceMessageSource extends AbstractMessageSource implements ResourceLoaderAware {

    /**
     *  定义资源文件名称
     */
    private final String resourceFileName = "rs.properties";

    /**
     *  定义资源文件名称
     */
    private final String resourcePath = "/META-INF/" + resourceFileName;

    /**
     *  定义字符集编码
     */
    private final String ENCODE = "UTF-8";

    /**
     * 通过Spring Context 接口回调注入 ResourceLoader
     */
    private ResourceLoader resourceLoader;

    /**
     *  rs Properties 文件资源
     */
    private final Resource rsPropertiesResource;

    /**
     *  rs Properties
     */
    private final Properties rsProperties;

    /**
     * 线程执行器
     */
    private final ExecutorService executorService;

    public DynamicResourceMessageSource() {
        this.rsPropertiesResource = getMessagePropertiesResource();
        this.rsProperties = loadMessageProperties();
        this.executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1));
        // 监听资源文件（Java NIO 2 WatchService）
        onMessagePropertiesChanged();
    }

    /**
     * 通过线程 异步 监控文件变化修改
     */
    private void onMessagePropertiesChanged() {
        //判断资源是否是文件
        if(this.rsPropertiesResource.isFile()){
            try {
                //获取对应文件系统中的文件信息
                final File rsFile = this.rsPropertiesResource.getFile();
                //获取文件的路径信息
                final Path rsFilePath = rsFile.toPath();
                //获取当前 OS 环境
                final FileSystem fileSystem = FileSystems.getDefault();
                //创建WatchService信息
                final WatchService watchService = fileSystem.newWatchService();
                //获取资源文件所在的目录信息
                final Path rsFileParentPath = rsFilePath.getParent();
                //注册 监控服务到 rsFileParentPath 中，注册文件修改事件
                rsFileParentPath.register(watchService,ENTRY_MODIFY);
                //开启异步线程处理文件变化事件
                processMessagePropertiesChanged(watchService);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 异步处理监控文件修改事件
     * @param watchService {@link WatchService}
     */
    private void processMessagePropertiesChanged(WatchService watchService) {
        this.executorService.execute(()->{
            while (true){
                WatchKey watchKey = null;
                try {
                    //一直take文件修改事件
                    watchKey = watchService.take();
                    //判断该次监控的key是否有效
                    if(watchKey.isValid()){
                        for(WatchEvent event : watchKey.pollEvents()){
                            final Watchable watchable = watchKey.watchable();
                            //监控的注册目录
                            Path rsPath = (Path) watchable;
                            // 事件所关联的对象即注册目录的子文件（或子目录）
                            // 事件发生源是相对路径
                            Path fileRelativePath = (Path) event.context();
                            //判断是否是需要监控的资源文件
                            if(this.resourceFileName.equals(fileRelativePath.getFileName().toString())){
                                //处理绝对路径
                                final Path filePath = rsPath.resolve(fileRelativePath);
                                //将Path变成File
                                File file = filePath.toFile();
                                //重新加载文件信息到本地
                                final Properties properties =
                                        loadMessageProperties(new FileReader(file));
                                //锁定关键对象,防止并发修改出现错误
                                synchronized (this.rsProperties){
                                    this.rsProperties.clear();
                                    this.rsProperties.putAll(properties);
                                }
                            }
                        }
                    }
                } catch (InterruptedException | FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if(watchKey != null){
                        //重置key
                        watchKey.reset();
                    }
                }
            }
        });
    }

    /**
     * 将资源文件转化成Properties
     * @return {@link Properties}
     */
    private Properties loadMessageProperties() {
        //将Resource变成编码Resource
        EncodedResource encodedResource = new EncodedResource(this.rsPropertiesResource,ENCODE);
        try {
            return loadMessageProperties(encodedResource.getReader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Properties();
    }

    /**
     * 将资源文件转化成Properties
     * @param reader {@link Reader} IO 流
     * @return {@link Properties}
     */
    private Properties loadMessageProperties(Reader reader) {
        Properties properties = new Properties();
        try {
            //加载Reader 到 Properties
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    /**
     *  获取定义的文件资源
     * @return {@link Resource}
     */
    private Resource getMessagePropertiesResource() {
        //获取资源加载器
        ResourceLoader resourceLoader = getResourceLoader();
        //加载路径下的资源
        return resourceLoader.getResource(this.resourcePath);
    }

    /**
     * 获取 ResourceLoader 对象
     * @return {@link ResourceLoader}
     */
    private ResourceLoader getResourceLoader() {
        return this.resourceLoader == null ? new DefaultResourceLoader() : this.resourceLoader;
    }

    /**
     * 处理资源文件
     * @param code 资源key
     * @param locale 本地环境
     * @return {@link MessageFormat}
     */
    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        final String messageCodeInfoPattern = this.rsProperties.getProperty(code);
        System.out.println("messageCodeInfoPattern -> "+messageCodeInfoPattern);
        if(StringUtils.hasText(messageCodeInfoPattern)){
            return new MessageFormat(messageCodeInfoPattern,locale);
        }
        return null;
    }

    /**
     * 通过Spring Context 接口回调注入ResourceLoader
     * @param resourceLoader {@link ResourceLoader}
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static void main(String[] args) {
        DynamicResourceMessageSource messageSource =
                new DynamicResourceMessageSource();
        for(int i=0;i<1000;i++) {
            final String name = messageSource.getMessage("name", new Object[]{}, Locale.getDefault());
            System.out.println(name);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
