package com.pjh.service;

import com.pjh.HelloService;

/**
 * @author yueyinghaibao
 */
public class HelloServiceImpl2 implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello: " + name;
    }
}
