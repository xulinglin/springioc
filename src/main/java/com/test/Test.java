package com.test;

/**
 * Created by XuLingLin on 2019/4/4
 */
public class Test {

    static final ThreadLocal<Demo> threadLocal = new ThreadLocal();



    public static void main(String[] args) throws InterruptedException {
        long time = System.currentTimeMillis();
        BeanFactory beanFactory = BeanFactory.getBeanFactory();

        //初始化Bean
        BeanDefinition beanInstantiation = new BeanDefinition("com.test.Demo");

        //设置bean的参数
        ParameterValue parameterValue = new ParameterValue("name","Spring IOC Instance Class");
        ParameterValues parameterValues = new ParameterValues();
        parameterValues.parameterValues.addLast(parameterValue);
        beanInstantiation.setParameterValues(parameterValues);

        BeanDefinitionInstance beanInstance = new BeanDefinitionInstance();
        beanInstance.setBean(beanInstantiation);
        //bean工厂处理
        beanFactory.setRegistering("demo",beanInstance);

        Demo demo = (Demo) beanFactory.getBean("demo");
        System.out.println(demo);
        System.out.println((System.currentTimeMillis() - time)/1000d);
    }

}
