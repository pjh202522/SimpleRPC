package com.pjh.proxy;

import com.pjh.common.Invocation;
import com.pjh.common.URL;
import com.pjh.loadbalance.LoadBalancer;
import com.pjh.protocal.HttpClient;
import com.pjh.register.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yueyinghaibao
 */
public class ProxyFactory {

    public static <T> T getProxy(Class interfaceClass, String version) {
        // 读取配置，决定动态代理

        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                //服务Mock
//                String mock = System.getProperty(interfaceClass.getName() + "Mock");
//                if(mock != null && mock.startsWith("return:")) {
//                    String result = mock.replace("return:", "");
//                    return result;
//                }

                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), version,
                        method.getParameterTypes(), args);

                HttpClient httpClient = new HttpClient();

                //服务发现
                List<URL> urls = RemoteRegister.get(interfaceClass.getName(), version);


                String result = null;
                List<URL> invokedUrls = new ArrayList<>();

                //服务重试
                int retry = 3;

                while(retry > 0) {
                    //休眠
                    try {
                        //负载均衡
                        urls.remove(invokedUrls);
                        URL url = LoadBalancer.random(urls);
                        invokedUrls.add(url);
                        //服务调用
                        result = httpClient.send(url.getHostname(), url.getPort(), invocation);
                        return result;
                    } catch (Exception e) {
                        if(--retry > 0) {
                            continue;
                        }
                        //服务容错
                        return "error!!";
                    }
                }
                return result;
            }
        });
        return (T) proxyInstance;
    }
}
