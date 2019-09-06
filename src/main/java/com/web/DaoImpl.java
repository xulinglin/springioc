package com.web;

/**
 * Created by XuLingLin on 2019/4/10
 */
public class DaoImpl implements Dao {

    public String Service(String name) {
        return "select * from test "+name;
    }
}
