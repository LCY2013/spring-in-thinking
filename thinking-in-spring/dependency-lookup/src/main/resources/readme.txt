层次性依赖查找
    层次性依赖查找接口-HierarchicalBeanFactory
        双亲BeanFactory: getParentBeanFactory()
        层次性查找
            根据Bean名称查找
                基于containsLocalBean方法实现
            根据Bean类型查找实例列表
                单一类型: BeanFactoryUtils#beanOfType
                集合类型: BeanFactoryUtils#beanOfTypeIncludingAncestors
            根据Java注解查找名称列表
                BeanFactoryUtils#beanNamesForAnnotationIncludingAncestors
延迟依赖查找
    Bean延迟依赖查找接口
        org.springframework.beans.factory.ObjectFactory

        org.springframework.beans.factory.ObjectProvider
            Spring5对java8特性扩展
                ObjectProvider#getIfAvailable
                ObjectProvider#ifAvailable
                ObjectProvider#iterator
                ObjectProvider#getIfUnique
                ObjectProvider#ifUnique
            Stream流式
                ObjectProvider#stream
                ObjectProvider#orderedStream



