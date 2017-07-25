package com.kanu_lp.support;

/**
 * Created by pragma1 on 09/06/2016.
 */
public class param {
    String name;
    String value;

    public param(String name , String value) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
