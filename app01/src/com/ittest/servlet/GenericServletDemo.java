package com.ittest.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GenericServletDemo",urlPatterns = {"/generic"},initParams = {
        @WebInitParam(name = "damin",value = "xiaoming")
})
public class GenericServletDemo extends GenericServlet {
    //父类实现了Serializable，加一个serialVersionUID子类也不会被序列化
    private static final long serialVersionUID = 62500890L;
    //通过扩展GGenericServlet代码变得非常整洁，都在父类完成了生命周期和其他方法，子类只需要实现自己需要的方法，而且不比自己亲自保存类级变量ServletConfig
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        //继承关系，类级变量直接使用父类的，初始化init也是使用的父类的初始化的，这里直接调用父类的getServletConfig即可，如果我们要自己
        //添加init()直接重写父类的无参init即可，会被容器在初始化有参的时候顺带初始化无参，因为父类的有参init调用了无参init。
        ServletConfig servletConfig = getServletConfig();
        String admin = servletConfig.getInitParameter("admin");
        servletResponse.setContentType("text/html");
        PrintWriter writer = servletResponse.getWriter();
        writer.println("<html><head></head><body>" +
                "Admin:" + admin +
                "</body></html>");
    }
}
