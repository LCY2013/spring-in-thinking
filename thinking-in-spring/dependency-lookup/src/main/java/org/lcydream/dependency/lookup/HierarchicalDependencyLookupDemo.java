package org.lcydream.dependency.lookup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.springframework.beans.factory.BeanFactoryUtils.originalBeanName;
import static org.springframework.beans.factory.BeanFactoryUtils.transformedBeanName;

/**
 * 层次性依赖查找示例
 */
public class HierarchicalDependencyLookupDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注入容器的配置类信息(Configuration)
        applicationContext.register(HierarchicalDependencyLookupDemo.class);
        //启动容器
        //applicationContext.refresh();

        //1、获取HierarchicalBeanFactory<-ConfigurableBeanFactory<-ConfigurableListableBeanFactory
        ConfigurableListableBeanFactory beanFactory =
                applicationContext.getBeanFactory();
        System.out.println("当前BeanFactory的Parent BeanFactory:"+beanFactory.getParentBeanFactory());
        //2、设置一个ParentBeanFactory
        HierarchicalBeanFactory parentBeanFactory = createBeanFactory();
        beanFactory.setParentBeanFactory(parentBeanFactory);
        System.out.println("当前BeanFactory的Parent BeanFactory:"+beanFactory.getParentBeanFactory());
        //查看是否包含某一种类型的BeanFactory
        displayLocalBean(beanFactory,"user");
        displayLocalBean(parentBeanFactory,"user");

        displayContainerBean(beanFactory,"user");
        displayContainerBean(parentBeanFactory,"user");

        //启动容器
        applicationContext.refresh();
        //停止容器
        applicationContext.close();
    }

    private static void displayContainerBean(HierarchicalBeanFactory beanFactory,String beanName){
        System.out.printf("BeanFactory [%s] 是否包含 bean name [%s] : %s\n",
                beanFactory,beanName,containsBean(beanFactory,beanName));
    }

    public static boolean containsBean(HierarchicalBeanFactory beanFactory, String name) {
        String beanName = transformedBeanName(name);
        if (beanFactory.containsBean(beanName)) {
            return !BeanFactoryUtils.isFactoryDereference(name);
        }
        // Not found -> check parent.
        BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
        if(parentBeanFactory instanceof HierarchicalBeanFactory) {
            HierarchicalBeanFactory parentHierarchicalBeanFactory =
                    HierarchicalBeanFactory.class.cast(parentBeanFactory);
            return containsBean(parentHierarchicalBeanFactory, originalBeanName(name));
        }
        return beanFactory.containsBean(beanName);
    }

    private static void displayLocalBean(HierarchicalBeanFactory beanFactory,String beanName){
        System.out.printf("BeanFactory [%s] 是否包含 bean name [%s] : %s\n",
                beanFactory,beanName,beanFactory.containsBean(beanName));
    }

    private static HierarchicalBeanFactory createBeanFactory(){
        //创建IOC容器,BeanFactory
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader =
                new XmlBeanDefinitionReader(beanFactory);
        //定义xml资源路径,classpath路径下
        String localPath = "classpath:/META-INF/dependency-lookup-context.xml";
        //加载配置文件
        beanDefinitionReader.loadBeanDefinitions(localPath);
        return beanFactory;
    }

}
