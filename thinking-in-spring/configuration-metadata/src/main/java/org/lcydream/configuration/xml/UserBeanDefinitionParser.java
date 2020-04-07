package org.lcydream.configuration.xml;

import org.lcydream.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @program: spring-in-thinking
 * @description: user 元素的解析实现 具体查看
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-07 21:37
 * @see {@link org.springframework.beans.factory.xml.BeanDefinitionParser}
 * @see {@link AbstractSingleBeanDefinitionParser}
 */
public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    /**
     * ID 号
     */
    private static final String ID = "id";

    /**
     * 用户名称
     */
    private static final String NAME = "name";

    /**
     * 城市列表
     */
    private static final String CITY = "city";

    /**
     * 工作城市列表
     */
    private static final String WORKCITIES = "workCities";

    /**
     * 生活城市列表
     */
    private static final String LIFECITIES = "lifeCities";

    /**
     * 配置文件地址
     */
    private static final String CONFIGFILELOCAL = "configFileLocal";

    @Override
    protected Class<?> getBeanClass(Element element) {
        return User.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        setAttribute(ID,element,builder);
        setAttribute(NAME,element,builder);
        setAttribute(CITY,element,builder);
        setAttribute(WORKCITIES,element,builder);
        setAttribute(LIFECITIES,element,builder);
        setAttribute(CONFIGFILELOCAL,element,builder);
    }

    private void setAttribute(String attributeName, Element element, BeanDefinitionBuilder builder) {
        String attributeValue = element.getAttribute(attributeName);
        if(StringUtils.hasText(attributeValue)){
            builder.addPropertyValue(attributeName,attributeValue);   //properties -> <key,value>
        }
    }

}
