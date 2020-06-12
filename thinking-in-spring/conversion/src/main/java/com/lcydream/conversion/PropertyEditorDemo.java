package com.lcydream.conversion;

import java.beans.PropertyEditor;

/**
 * @program: spring-in-thinking
 * @description: {@link PropertyEditor} 示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-06-08 21:05
 * @see PropertyEditor
 */
public class PropertyEditorDemo {

    public static void main(String[] args) {
        //定义一段文本
        String text = "age=18"+System.getProperty("line.separator")+"name=luo";
        //获取自定义的PropertyEditor
        PropertyEditor propertyEditor = new StringToPropertiesPropertyEditor();
        //将自定义文本装入自定义属性编辑器中
        propertyEditor.setAsText(text);
        //输出编辑后的信息
        System.out.println(propertyEditor.getValue());
        System.out.println(propertyEditor.getAsText());
    }

}
