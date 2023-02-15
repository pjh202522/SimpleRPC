package com.pjh.service;

import com.pjh.HelloService;

/**
 * @author yueyinghaibao
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello: " + name;
    }
}
