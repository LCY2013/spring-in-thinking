/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-07-29
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.lcydream.beans.lifecycle.info;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @program: spring-in-thinking
 * @description: 测试@Value问题
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-29
 */
@Configuration
@PropertySource("classpath:/user.properties")
@ComponentScan("org.lcydream.beans.lifecycle.info")
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.register(Application.class);
        context.refresh();

        System.out.println(context.getBean(User.class));

        context.close();
    }

}

/*
  Spring 不同版本在Context和Beans模块中你需要知道@Value注解字符串不能解析问题说明?
   属性注入阶段:
   第一阶段:
   >= spring4.3.27
   容器初始化:
   org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization ->
    org.springframework.beans.factory.support.AbstractBeanFactory.addEmbeddedValueResolver() ->
   获取User对象:
   从查询对象到构建对象的元信息
   org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean ->
    org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean ->
     org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean ->
      org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyMergedBeanDefinitionPostProcessors ->
       org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessMergedBeanDefinition ->
        org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.findAutowiringMetadata ->
         org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.buildAutowiringMetadata ->
            属性赋值
           org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean ->
            org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessProperties ->
              org.springframework.beans.factory.annotation.InjectionMetadata.inject ->
                org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.AutowiredFieldElement.inject ->
                    org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency ->
                      org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency ->
                        org.springframework.beans.factory.support.AbstractBeanFactory.resolveEmbeddedValue ->

    下面代码
    org.springframework.beans.factory.support.AbstractBeanFactory.resolveEmbeddedValue()
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
			result = resolver.resolveStringValue(result);
			if (result == null) {
				return null;
			}
		}
	这里的this.embeddedValueResolvers
	< spring4.3.27  没有注入一个StringValueResolver 实现
	>=spring4.3.27 && <spring5.X 注入一个实现
	    代码位置:
	        org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization()
	            // 不存在内置值转换器的时候就会注入下面的解析器
	            if (!beanFactory.hasEmbeddedValueResolver()) {
                    beanFactory.addEmbeddedValueResolver(new StringValueResolver() {
                        @Override
                        public String resolveStringValue(String strVal) {
                            return getEnvironment().resolvePlaceholders(strVal);
                        }
                    });
                }
	>= spring5.X  默认注入一个表达式实现的StringValueResolver
	    代码位置:
	        org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization()
	            // 不存在内置值转换器的时候就会注入下面的解析器
	            if (!beanFactory.hasEmbeddedValueResolver()) {
                    beanFactory.addEmbeddedValueResolver(strVal -> getEnvironment().resolvePlaceholders(strVal));
                }

     也许你会发现在Spring MVC中不存在这样的问题？
        这是因为SpringMVC中会默认
        org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver.resolveArgument ->
         org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver.resolveStringValue ->
 */
