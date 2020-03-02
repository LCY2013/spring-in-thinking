package org.lcydream.container;

import org.lcydream.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * {@link BeanFactory}IoC容器示例
 */
public class BeanFactoryIoCContainer {

    public static void main(String[] args) {
        //创建IOC容器,BeanFactory
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader =
                new XmlBeanDefinitionReader(beanFactory);
        //定义xml资源路径,classpath路径下
        String localPath = "classpath:/META-INF/dependency-lookup-context.xml";
        //加载配置文件
        int beanCount = beanDefinitionReader.loadBeanDefinitions(localPath);
        System.out.println("beans 数量:"+beanCount);
        lookUpCollectionType(beanFactory);
    }

    /**
     * 按集合类型查找
     *  {@link ListableBeanFactory}
     * @param beanFactory 工厂
     */
    private static void lookUpCollectionType(BeanFactory beanFactory) {
        //org.springframework.beans.factory.ListableBeanFactory
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("通过类型查询一个集合数据:"+beansOfType);
        }
    }
}
