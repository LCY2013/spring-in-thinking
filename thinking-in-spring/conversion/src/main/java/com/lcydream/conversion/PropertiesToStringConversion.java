package com.lcydream.conversion;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalConverter;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

/**
 * @program: spring-in-thinking
 * @description: 自定义转换器 {@link ConditionalGenericConverter}
 *  {@link Properties} -> {@link String}
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-06-12 17:35
 * @see ConditionalConverter
 * @see GenericConverter 复合类型转换常用
 * @see Converter  单一类型转换常用
 */
public class PropertiesToStringConversion implements ConditionalGenericConverter {

    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        System.out.println("com.lcydream.conversion.PropertiesToStringConversion.matches");
        return Properties.class.equals(sourceType.getObjectType())
                && String.class.equals(targetType.getObjectType());
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        System.out.println("com.lcydream.conversion.PropertiesToStringConversion.getConvertibleTypes");
        return Collections.singleton(new ConvertiblePair(Properties.class,String.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        System.out.println("com.lcydream.conversion.PropertiesToStringConversion.convert");
        //4、获取存储的数据
        Properties properties = (Properties)source;
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
