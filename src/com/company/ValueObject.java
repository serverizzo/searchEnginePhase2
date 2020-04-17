package com.company;

import com.sun.jdi.Value;

import java.io.Serializable;

public class ValueObject implements Serializable {

    String[] val;

    ValueObject(String documentFoundIn, Integer position){
        this.val = new String[] {documentFoundIn, position.toString()};
    }

    public void print(){
        System.out.println(val[0] + " " + val[1]);
    }

    public String toString(){
        return val[0] + " " + val[1];
    }


}
