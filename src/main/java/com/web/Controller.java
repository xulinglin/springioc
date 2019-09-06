package com.web;

/**
 * Created by XuLingLin on 2019/4/10
 */
public class Controller {

    public Service service;

    public void run(String name){
        System.out.println(service.Service(name));
    }
}
