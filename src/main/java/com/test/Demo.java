package com.test;

/**
 * Created by XuLingLin on 2019/4/4
 */
public class Demo {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name ;
    }

    @Override
    public String toString() {
        return "demo{" +
                "name='" + name + '\'' +
                '}';
    }
}
