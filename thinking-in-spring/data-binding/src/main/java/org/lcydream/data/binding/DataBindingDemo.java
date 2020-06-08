package org.lcydream.data.binding;

import org.lcydream.data.binding.domain.Student;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: spring-in-thinking
 * @description: 数据绑定 {@link DataBinder} 示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-06-07 16:05
 */
public class DataBindingDemo {

    public static void main(String[] args) {
        //创建一个Student对象
        Student student = new Student();
        //1、创建一个DataBinder对象
        DataBinder binder = new DataBinder(student);
        //2、构建一个PropertiesValues
        Map<String,Object> sources = new HashMap<>();
        sources.put("id",75);
        sources.put("name","fufeng");

        //PropertyValues新增一个Student中没有的属性
        //DataBinder 特性一: 忽略不存在的属性
        sources.put("age",18);

        //PropertyValues中支持一个嵌套对象school
        //DataBinder 特性二: 支持嵌套属性设置
        /*
            School school = new School();
            school.setName("川大");
            student.setSchool(school);
         */
        //sources.put("school",new School());
        sources.put("school.name","川大");

        //开始构建PropertiesValues对象
        PropertyValues propertyValues = new MutablePropertyValues(sources);
        //开始调整DataBinder特性属性
        // IgnoreUnknownFields 默认true -> 调整后false ,调整后没有age属性会抛出异常
        //org.springframework.beans.NotWritablePropertyException: Invalid property 'age' of bean class
        //binder.setIgnoreUnknownFields(false);

        // 调整嵌套路径自动生成 默认true -> false , 调整后需要先注册school对象，然后在绑定school.name，否则会抛出
        //org.springframework.beans.NullValueInNestedPathException: Invalid property 'school' of bean class
        //也可以调整忽略字段错误
        binder.setAutoGrowNestedPaths(false);

        // 调整忽略错误的字段设置 默认false -> true , 调整后存在错误的字段就不会进行绑定
        binder.setIgnoreInvalidFields(true);

        //设置必须要的字段
        binder.setRequiredFields("id","name","addr");

        //3、开始属性绑定
        binder.bind(propertyValues);
        //4、输出绑定后的对象信息
        System.out.println(student);

        //5、获取绑定后的结果(结果包含错误文案 code，不会抛出异常)
        final BindingResult bindingResult = binder.getBindingResult();
        System.out.println(bindingResult);
    }

}
