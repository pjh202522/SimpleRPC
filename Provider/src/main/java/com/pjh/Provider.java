package com.pjh;

import com.pjh.common.URL;
import com.pjh.protocal.HttpServer;
import com.pjh.register.LocalRegister;
import com.pjh.register.RemoteRegister;
import com.pjh.service.HelloServiceImpl;
import com.pjh.service.HelloServiceImpl2;
import com.pjh.service.TimeServiceImpl;

/**
 * @author yueyinghaibao
 */
public class Provider {
    public static void main(String[] args) {
        URL url = new URL("localhost", 8081);

        LocalRegister.register(HelloService.class.getName(), "1.0", HelloServiceImpl.class);
        LocalRegister.register(HelloService.class.getName(), "2.0", HelloServiceImpl2.class);
        LocalRegister.register(TimeService.class.getName(), "1.0", TimeServiceImpl.class);
        //注册中心注册 服务注册
        //读取配置填入URL

        RemoteRegister.register(HelloService.class.getName(), "1.0", url);
        RemoteRegister.register(HelloService.class.getName(), "2.0", url);
        RemoteRegister.register(TimeService.class.getName(), "1.0", url);

        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(), url.getPort());
    }
}
