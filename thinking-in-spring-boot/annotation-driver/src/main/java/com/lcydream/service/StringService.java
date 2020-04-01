package com.lcydream.service;

import com.lcydream.annotation.ServiceImpl;

@ServiceImpl
public class StringService {

    public void print(String content){
        System.out.println(this+":"+content);
    }
}
