package com.pjh.protocal;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * @author yueyinghaibao
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) {
        new HttpServerHandler().handler(req, res);
    }
}
