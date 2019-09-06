package com.test;

/**
 * Created by XuLingLin on 2019/4/4
 */
public class BeanDefinition {

    public BeanDefinition(){

    }

    public BeanDefinition(String className){
        this.setRegisteringBean(className);
    }

    //当前类的实例
    private Class<?> clazz;
    //当前类的全限定名
    private String className;
    //参数
    private ParameterValues parameterValues;

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ParameterValues getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(ParameterValues parameterValues) {
        this.parameterValues = parameterValues;
    }

    public void setRegisteringBean(String className){
        this.className = className;
        try {
            this.clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
