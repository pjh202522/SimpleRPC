package com.pjh.protocal;

import com.pjh.common.Invocation;
import com.pjh.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yueyinghaibao
 */
public class HttpServerHandler {
    public void handler(ServletRequest req, ServletResponse res) {
        // 处理请求 —— 接口、方法、参数
        // 判断序列化
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            String interfaceName = invocation.getInterfaceName();
            String version = invocation.getVersion();

            // 或者读取配置中心的默认值
            Class implClass = LocalRegister.get(interfaceName, version != null ? version : "1.0");
            Method method = implClass.getMethod(invocation.getMethodName(), invocation.getParametersTypes());

            String result = (String) method.invoke(implClass.newInstance(), invocation.getParameters());

            IOUtils.write(result, res.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
