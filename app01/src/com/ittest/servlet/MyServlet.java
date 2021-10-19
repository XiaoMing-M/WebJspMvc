package com.ittest.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 此处实现了servlet接口，该接口有5个方法，为最原始的使用java web的接口！
 */
//该注解给这个servlet程序起了一个名字，并给与了调用servlet的方式通过url访问
@WebServlet(name="/MyServlet",urlPatterns = "/my")
public class MyServlet implements Servlet {
    //transient修饰的对象属性，当对象被序列化，该属性例外，这里提供了一个类级变量servletConfig
    private transient ServletConfig servletConfig;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化的时候将tomcat提供的servletconfig类赋予类级变量给我们使用！
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        //当用户发送请求将servlet的名字返回到客户端给用户展示
        String servletName = servletConfig.getServletName();
        servletResponse.setContentType("text/html");
        PrintWriter writer = servletResponse.getWriter();
        writer.println("<html><head></head>"+"<body>hello "+servletName+"</body></html>");
    }

    @Override
    public String getServletInfo() {
        return "myServlet";
    }

    @Override
    public void destroy() {

    }
}
