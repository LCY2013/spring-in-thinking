package com.lcydream.controller;

import com.lcydream.annotation.ServiceImpl;
import com.lcydream.service.StringService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceImpl
public class StringController implements InitializingBean, DisposableBean, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Autowired
    StringService stringService;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println(beanFactory.getBean(StringService.class));
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("StringController 开始销毁");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        stringService.print("开始输出");
    }
}
