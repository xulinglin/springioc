package com.test;

/**
 * Created by XuLingLin on 2019/4/4
 */
public class ParameterValue {

    public ParameterValue(){
        this(null,null);
    }

    public ParameterValue(String name,Object value){
        this.name = name;
        this.value = value;
    }

    //参数名称
    private String name;
    //参数值
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
