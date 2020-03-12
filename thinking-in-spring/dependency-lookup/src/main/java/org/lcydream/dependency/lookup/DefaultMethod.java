package org.lcydream.dependency.lookup;

/**
 * 测试接口默认方法的个数
 */
public interface DefaultMethod {

    default void ifUnique(){

    }

    default Object getObject(){
        return null;
    }
}
