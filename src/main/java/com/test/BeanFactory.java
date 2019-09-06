package com.test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by XuLingLin on 2019/4/4
 * 工厂
 */
public class BeanFactory {

    private static BeanFactory factory = new BeanFactory();

    private static Map<String,Object> beanFactory = new ConcurrentHashMap<String, Object>();

    private static Map<String,BeanDefinitionInstance> beanInstance = new ConcurrentHashMap<String, BeanDefinitionInstance>();

    public static ThreadLocal<Object> threadLocal =new ThreadLocal<Object>();

    private BeanFactory(){

    }

    public Object getBean(String beanName){
        Object value = null;
        if (null == (value = threadLocal.get())){
            value = getBeanDefinition(beanName);
            threadLocal.set(value);
        }
        return threadLocal.get();
    }

    public Object getBeanDefinition(String beanName){
        Object value = null;
        if(null == (value = this.beanFactory.get(beanName))){
            if (null == value)
                value = createBean(beanName, value);
            this.beanFactory.put(beanName, value);
        }
        return value;
    }

    private Object createBean(String beanName,Object value){
        BeanDefinitionInstance beanInstance = this.beanInstance.get(beanName);
        if(null == beanInstance )
            throw  new NullPointerException("没有注册bean的实例");
        try {
            value = beanInstance.getBean().getClazz().newInstance();
            copyOnParameter(value,beanInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void setRegistering(String name,BeanDefinitionInstance beanInstance) {
       this.beanInstance.put(name,beanInstance);
    }

    private void copyOnParameter(Object value,BeanDefinitionInstance beanInstance) throws Exception {
        BeanDefinition bean = beanInstance.getBean();
        ParameterValues parameterValues = bean.getParameterValues();
        ParameterValue poll = null;
        if(parameterValues.parameterValues.size() > 0){
            while (null != (poll=parameterValues.parameterValues.poll())){
                Field field = value.getClass().getDeclaredField(poll.getName());
                field.setAccessible(true);
                field.set(value, poll.getValue());
            }
        }
    }

    public static BeanFactory getBeanFactory(){
        return BeanFactory.factory;
    }
}
