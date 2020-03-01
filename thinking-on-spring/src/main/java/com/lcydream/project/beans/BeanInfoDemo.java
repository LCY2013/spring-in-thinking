package com.lcydream.project.beans;

import java.beans.*;
import java.util.stream.Stream;

/**
 * {@link java.beans.BeanInfo} 示例
 */
public class BeanInfoDemo {


    public static void main(String[] args) throws IntrospectionException {
        //BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class,Object.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        //Stream.of(propertyDescriptors).forEach(System.out::println);
        Stream.of(propertyDescriptors).forEach(propertyDescriptor -> {
            //PropertyDescriptor 允许添加属性编辑器 -PropertyEditor
            //GUI -> text(String) -> PropertyType
            //name -> String
            //age -> Integer
            Class<?> propertyType = propertyDescriptor.getPropertyType();
            String typeName = propertyType.getName();
            if("age".equals(typeName)){  //属性名称为age，字段/属性 ，添加PropertyEditor
                //String -> Integer
                //Integer.valueOf()
                propertyDescriptor.setPropertyEditorClass(StringToIntegerPropertyEditor.class);
            }
        });
    }

    //Spring 3.0以前常用参数类型转换
    static class StringToIntegerPropertyEditor extends PropertyEditorSupport{
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Integer integer = Integer.valueOf(text);
            setValue(text);
        }
    }
}
