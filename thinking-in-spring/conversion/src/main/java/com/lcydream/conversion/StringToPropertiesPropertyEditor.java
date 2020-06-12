package com.lcydream.conversion;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * @program: spring-in-thinking
 * @description:  String -> property {@link PropertyEditor}
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-06-08 21:09
 * @see PropertyEditor
 * @see PropertyEditorSupport
 */
public class StringToPropertiesPropertyEditor extends PropertyEditorSupport {

    /**
     * 重新实现setAsText方法
     * @param text 文本对象
     * @throws IllegalArgumentException 错误异常
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        //1、将String 文本转换成Property对象
        Properties properties = new Properties();
        //加载文本数据到Properties中
        try {
            properties.load(new StringReader(text));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2、将转换后的properties对象存储
        setValue(properties);
        //3、接下来就是使用getValue()使用properties对象
    }

    /**
     * 获取转换后的数据对象
     * @return 转换后的数据对象
     */
    @Override
    public String getAsText() {
        //4、获取存储的数据
        Properties properties = (Properties)getValue();
        //5、将properties对象中的数据全部取出来
        StringBuilder stringBuilder = new StringBuilder();
        properties.forEach((key, value) ->
            stringBuilder.append(key)
                    .append("=").append(value)
                    .append(System.getProperty("line.separator"))
        );
        return stringBuilder.toString();
    }
}
