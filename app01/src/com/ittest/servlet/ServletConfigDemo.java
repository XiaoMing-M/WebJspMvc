package com.ittest.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

//这里就给servletconfig设置了一些初始参数我们可以通过方法获取值
@WebServlet(name = "ServletConfigDemo", urlPatterns = {"/config"}, initParams = {
        @WebInitParam(name = "admin", value = "1234"),
        @WebInitParam(name = "email", value = "2222")
})
public class ServletConfigDemo implements Servlet {
    private transient ServletConfig servletConfig;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.servletConfig = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return servletConfig;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        ServletConfig servletConfig = getServletConfig();
        //可以获取上下文对象
        ServletContext servletContext = servletConfig.getServletContext();
        //可以获取部署描述符或者注解中的初始化参数
        String email = servletConfig.getInitParameter("email");
        servletResponse.setContentType("text/html");
        PrintWriter writer = servletResponse.getWriter();
        writer.println("<html><head></head><body>" +
                "<br/>Email:" + email +
                "</body></html>");
    }

    @Override
    public String getServletInfo() {
        return "xiaoming";
    }

    @Override
    public void destroy() {

    }
}
