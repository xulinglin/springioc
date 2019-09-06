package com.web;

/**
 * Created by XuLingLin on 2019/4/10
 */
public class ServiceInterface implements Service {

    private Dao dao;

    public String Service(String name) {
        return dao.Service(name);
    }
}
