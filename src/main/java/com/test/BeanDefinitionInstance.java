package com.test;

/**
 * Created by XuLingLin on 2019/4/4
 */
public class BeanDefinitionInstance {
    //bean的实例
    private BeanDefinition bean;
    //bean的实例名称
    private String beanName;

    public BeanDefinition getBean() {
        return bean;
    }

    public void setBean(BeanDefinition bean) {
        this.bean = bean;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
