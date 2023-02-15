package com.pjh;

import com.pjh.common.Invocation;
import com.pjh.protocal.HttpClient;
import com.pjh.proxy.ProxyFactory;

/**
 * @author yueyinghaibao
 */
public class Consumer {
    public static void main(String[] args) {
        TimeService timeService = ProxyFactory.getProxy(TimeService.class, "1.0");
        String result = timeService.getTime();
        System.out.println(result);
    }
}
