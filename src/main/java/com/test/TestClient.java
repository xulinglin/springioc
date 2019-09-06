package com.test;

public class TestClient extends Thread
    {
       private Demo sn;
       public TestClient(Demo sn) {

       }
       public void run()
       {
           this.sn = (Demo) BeanFactory.getBeanFactory().getBean("demo");
           System.out.println("thread[" + Thread.currentThread().getName()+"]sn[" + sn.getName() + "]");
           for (int i = 0; i <5; i++) {
               // ④每个线程打出3个序列值
               sn.setName(""+i);
               System.out.println("thread[" + Thread.currentThread().getName()+"]sn[" + sn.getName() + "]");
           }
       }
    }