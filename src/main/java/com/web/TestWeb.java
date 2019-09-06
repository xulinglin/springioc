package com.web;

import com.test.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by XuLingLin on 2019/4/10
 */
public class TestWeb {

    public static void main(String[] args) {
        BeanFactory beanFactory = BeanFactory.getBeanFactory();

        //注册Dao
        BeanDefinition beanInstantiation0 = new BeanDefinition("com.web.DaoImpl");
        ParameterValues parameterValues0 = new ParameterValues();
        beanInstantiation0.setParameterValues(parameterValues0);
        BeanDefinitionInstance beanDefinitionInstance0 = new BeanDefinitionInstance();
        beanDefinitionInstance0.setBean(beanInstantiation0);
        beanDefinitionInstance0.setBeanName("dao");
        beanFactory.setRegistering("dao",beanDefinitionInstance0);
        Dao dao = (Dao) beanFactory.getBeanDefinition("dao");

        //注册Service
        BeanDefinition beanInstantiation1 = new BeanDefinition("com.web.ServiceInterface");
        ParameterValues parameterValues1 = new ParameterValues();
        parameterValues1.parameterValues.add(new ParameterValue("dao",dao));
        beanInstantiation1.setParameterValues(parameterValues1);

        BeanDefinitionInstance beanDefinitionInstance1 = new BeanDefinitionInstance();
        beanDefinitionInstance1.setBean(beanInstantiation1);
        beanDefinitionInstance1.setBeanName("service");
        beanFactory.setRegistering("service",beanDefinitionInstance1);

        Service service = (Service) beanFactory.getBeanDefinition("service");

        //注册Controller
        BeanDefinition beanInstantiation2 = new BeanDefinition("com.web.Controller");
        ParameterValues parameterValues2 = new ParameterValues();
        parameterValues2.parameterValues.add(new ParameterValue("service",service));
        beanInstantiation2.setParameterValues(parameterValues2);

        BeanDefinitionInstance beanDefinitionInstance2= new BeanDefinitionInstance();
        beanDefinitionInstance2.setBean(beanInstantiation2);
        beanDefinitionInstance2.setBeanName("controller");
        beanFactory.setRegistering("controller",beanDefinitionInstance2);
        final Object demo = BeanFactory.getBeanFactory().getBeanDefinition("controller");
        for (int i = 0; i < 20; i++) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        Method run = demo.getClass().getMethod("run", String.class);
                        run.invoke(demo,Thread.currentThread().getName());
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
