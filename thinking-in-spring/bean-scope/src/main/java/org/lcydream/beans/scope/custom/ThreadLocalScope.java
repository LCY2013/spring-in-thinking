package org.lcydream.beans.scope.custom;


import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义线程范围的作用域实现
 * @See {@link org.springframework.beans.factory.config.Scope}
 */
public class ThreadLocalScope implements Scope {

    public static final String THREAD_LOCAL = "THREAD-LOCAL";

    //创建容器上下文
    private final ThreadLocal<Map<String, Object>> threadScope =
            new NamedThreadLocal<Map<String, Object>>("THREAD-LOCAL-SCOPE") {
                @Override
                protected Map<String, Object> initialValue() {
                    //根据线程创建一个上下文
                    return new HashMap<>();
                }
            };

    /**
     * 获取线程范围内的上下文
     * @return 线程范围内的上下文
     */
    private Map<String,Object> getContext(){
        return threadScope.get();
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        final Map<String, Object> context = getContext();
        final Object ret = context.get(name);
        if(ret == null){
            Object objectFactoryObject = objectFactory.getObject();
            context.put(name,objectFactoryObject);
            return objectFactoryObject;
        }
        return ret;
    }

    @Override
    public Object remove(String name) {
        final Map<String, Object> context = getContext();
        final Object ret = context.get(name);
        context.remove(name);
        return ret;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        /**
         * TODO
         */
        System.out.println("Thread-local:"+name);
        callback.run();
    }

    @Override
    public Object resolveContextualObject(String key) {
        return getContext().get(key);
    }

    @Override
    public String getConversationId() {
        return String.valueOf(Thread.currentThread().getId());
    }
}
