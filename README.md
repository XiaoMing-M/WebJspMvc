javajsp与mvc初学指南  
app01 

servlet 技术属于java web中非常重要的一门技术，是一个java程序。一个servlet应用有多个servelt程序，jsp页面也会被转换编译成servelt程序，因此servlet应用可以说是servlet与jsp的应用。servlet应用无法独立运行，需要依靠容器运行，也就是tomcat容器，tomcat可以处理servlet应用与静态内容，http服务器（tomcat容器子模块）通常处理静态资源，用于将web客户端用户请求传递给servlet应用，并将结果返回给客户端，从客户端到服务器使用http协议通信。

servlet api  有4个java包  
javax.servlet:定义包含servelt与servlet容器之间规范与接口  
javax.servelt.http:定义http servlet与servlet 容器之间的规范与接口  
javax.servlet.annotation:定义注解元数据的，比如@servlet @filter @listener 等注解  
javax.servelt.descriptor:定义包含程序化登录web应用程序的配置信息的类型

javax.servlet中有哪些接口？</br>
servlet接口，servletrequest接口，servletresponse接口，servletcontext接口，servletconfig接口，requsetdispatcher接口，filter接口
核心技术是servlet接口，所以servlet类必须直接或者间接的实现servlet接口，servlet接口并不是直接被类实现，中间还有一个抽象类genericservlet，我们是去实现这个抽象类的。每个servlet接口都只有一个实例，由servlet容器去将类加载入内存并在实例上调用具体方法。大致流程为，用户请求导致tomcat容器调用servlet接口实现类的service方法，并传入servletrequest实例和servletresponse实例，servletrequest封装了当前的http请求，servletresponse封装了给用户的响应。另外每个servlet应用还会创建一个servletcontext实例，该实例封装了环境的上下文详情与创建一个封装了servlet配置的servletconfig实例。

servlet接口中的5个方法？  
生命周期方法有3个  init，service ，destory    
init：servlet应用第一次被请求会调用该方法，用于初始化工作，调用这个方法，servlet容器会传入一个servletconfig，由我们给一个类级变量去初始化    
service：每当请求servlet时，就会调用该方法去处理请求  
destory：当servlet应用要被销毁时(服务器重启或停止)会调用这个方法，用于关闭资源
getservletinfo：返回servlet的描述，比如作者，版权，版本信息等
getservlerconfig：返回由servlet容器传给init方法的servletconfig，我们给了类级变量，就是返回我们给的类

servlet的安全问题是怎么出现的？  
首先多个客户端发送请求，服务器会为每个请求创建一个线程去访问servlet的service方法，然后访问相应的数据，访问方法没有任何问题，因为线程间独立，但是出现成员变量等会有安全问题，因为线程共享，这个时候可以使用绑定线程的threadlocal去存变量数据。还有方法就是servlet实现SingleThreadModel接口，这个时候servlet应用会为每个线程的访问创建一个servlet实例，这样service方法间都是相互独立的了，但是这样只是解决安全问题，并不是解决并发问题的那种，因为并发指的是多个线程调用一个实例的情况，而且会让系统开销变得非常大，已经废弃。并发安全我们可以考虑使用synchronized同步代码块，service方法中在使用变量的时候代码块包裹住，保证只有一个线程使用，但是无法处理高并发，会导致性能特别差。最好的办法就是将变量放在方法中，不适用成员变量，实在没办法就threadlocal或者使用java.util.concurrent.atomic包的成员。























