javajsp与mvc初学指南  
app01 

servlet 技术属于java web中非常重要的一门技术，是一个java程序。一个servlet应用有多个servelt程序，jsp页面也会被转换编译成servelt程序，因此servlet应用可以说是servlet与jsp的应用。servlet应用无法独立运行，需要依靠容器运行，也就是tomcat容器，tomcat可以处理servlet应用与静态内容，http服务器（tomcat容器子模块）通常处理静态资源，用于将web客户端用户请求传递给servlet应用，并将结果返回给客户端，从客户端到服务器使用http协议通信。

servlet api  有4个java包  
javax.servlet:定义包含servelt与servlet容器之间规范与接口  
javax.servelt.http:定义http servlet与servlet 容器之间的规范与接口  
javax.servlet.annotation:定义注解元数据的，比如@servlet @filter @listener 等注解  
javax.servelt.descriptor:定义包含程序化登录web应用程序的配置信息的类型

javax.servlet中有哪些接口？  
servlet接口，servletrequest接口，servletresponse接口，servletcontext接口，servletconfig接口，requsetdispatcher接口，filter接口
核心技术是servlet接口，所以servlet类必须直接或者间接的实现servlet接口，servlet接口并不是直接被类实现，中间还有一个抽象类genericservlet，我们是去实现这个抽象类的。每个servlet接口都只有一个实例，由servlet容器去将类加载入内存并在实例上调用具体方法。大致流程为，用户请求导致tomcat容器调用servlet接口实现类的service方法，并传入servletrequest实例和servletresponse实例，servletrequest封装了当前的http请求，servletresponse封装了给用户的响应。另外每个servlet应用还会创建一个servletcontext实例，该实例封装了环境的上下文详情。还创建一个封装了servlet配置的servletconfig实例。

servlet接口中的5个方法？  
生命周期方法有3个  init，service ，destory    
init：servlet应用第一次被请求会调用该方法，用于初始化工作，调用这个方法，servlet容器会传入一个servletconfig，供我们使用   
service：每当请求servlet时，就会调用该方法去处理请求  
destory：当servlet应用要被销毁时(服务器重启或停止)会调用这个方法，用于关闭资源
getservletinfo：返回servlet的描述，比如作者，版权，版本信息等
getservlerconfig：返回由servlet容器传给init方法的servletconfig，提供相关配置信息我们可以使用类级变量接受后，供我们使用

servlet的安全问题是怎么出现的？  
首先多个客户端发送请求，服务器会为每个请求创建一个线程去访问servlet的service方法，然后访问相应的数据，访问方法没有任何问题，因为线程间独立，但是出现成员变量等会有安全问题，因为线程共享，这个时候可以使用绑定线程的threadlocal去存变量数据。还有方法就是servlet实现SingleThreadModel接口，这个时候servlet应用会为每个线程的访问创建一个servlet实例，这样service方法间都是相互独立的了，但是这样只是解决安全问题，并不是解决并发问题的那种，因为并发指的是多个线程调用一个实例的情况，而且会让系统开销变得非常大，已经废弃。并发安全我们可以考虑使用synchronized同步代码块，service方法中在使用变量的时候代码块包裹住，保证只有一个线程使用，但是无法处理高并发，会导致性能特别差。最好的办法就是将变量放在方法中，不适用成员变量，实在没办法就threadlocal或者使用java.util.concurrent.atomic包的成员。

idea如何配置tomcat容器构建第一个java web文件呢？   
新建一个空项目并创建一个java模块，给这个项目添加一个tomcat容器，点击添加配置，添加一个tomcat的server，指定版本就行。点击文件，点击项目结构，需要添加2个jar包到libraries下，在安装好的tomcat的文件夹的lib文件夹下有这2个jar包，叫jsp-api.jar和servlet-api.jar。在项目结构中最后一个artifacts中添加web application ：exploded。在项目结构中facets中添加一个名叫web的web框架，并在tomcat的配置中的deployment中添加这个artifacts到容器。最后在modules中的paths下配置tomcat的输出路径为WEB-INF\classes下即可。这个classes文件夹使用来存放编译后的class类的，这里我们已经配置编译后的文件放入classes中

servletrequest接口和servletresponse接口?  
对于每一个http请求，servelt容器都会创建一个servletrequest实例，并传给service方法，接口中有一些方法可以获取请求信息。  
public String getContentType() ：返回请求主体的MIME类型  
public String getParameter(String name)：获取指定请求参数的值，url中或者请求体中的都可以  
对于每一个http请求，servelt容器都会创建一个servletresponse实例，并传给service方法，用于向浏览器发送响应  
public PrintWriter getWriter()：PrintWriter对象的println()用于向浏览器响应数据，大多数情况作为html文本格式发送  
所以在发送请求之前应通过servletresponse的setContentType设置发送的数据格式让浏览器识别。

servletconfig接口?  
在servlet容器初始化servlet时会给init方法传入一个servletconfig，该方法封装的配置信息，我们可以在部署描述符或者@webservlet注解中赋予。  
String getInitParameter(String name)：获取指定名称的初始参数值  
String getServletName()：获取应用的名字  
Enumeration<String> getInitParameterNames()：获取所有初始化参数值并返回枚举  
该接口还提供了一个获取上下文对象servletcontext的方法getServletContext()

ServletContext接口？  
ServletContext表示Servlet应用程序，每一个web应用只有一个，通过在ServletConfig中调用getServletContext()方法可以获取ServletContext，有了这个
我们就可以共享从应用程序中访问到的信息，这些信息被保存在ServletContext中的一个内部Map中。  并提供如下方法进行代码操作
Object getAttribute(String name)：获取通过访问map中的key获取值  
Enumeration<String> getAttributeNames()：获取所有，并返回枚举类  
setAttribute(String name, Object object)：存储某些数据到map中  
removeAttribute(String name)：移除map中的某些数据
  
GenericServlet抽象类？  
servlet接口并不是直接被类实现，中间还有一个抽象类genericservlet，这个是干啥的？  
如果没有这个类，那么我们每次实现Servlet都需要实现5个方法，3个生命周期，2个获取信息的方法，即便我们不使用也必须要实现，因为这个是接口的特性，必须实现，还有一些额外的代码，比如需要在初始化方法init的参数中接收由容器传过来的servletconfig，并完成ServletConfig对象保存到类级变量中，以便在使用getservlerconfig()时可以获取配置对象，这是非常繁琐和麻烦的，于是出现了这个抽象类。实现了2个接口，servlet和servletconfig，并将容器给的servletconfig给了类级变量，我们可以直接获取，提供了2个接口的默认实现，我们只需实现servlet方法即可。  
GenericServlet实现了2个init方法，这是为什么呢？  
因为容器只会给有参的init赋予ServletConfig对象，我们需要继承这个GenericServlet抽象类，父类如果只有一个init(ServletConfig config)方法，我们就必须使用init(ServletConfig config)方法去覆盖父类的这个方法，必须使用super.init(servletConfig)给父类类级变量赋值，这样容器会给子类传递的config赋予给父类的类级变量，我们在调用父类的getServletConfig()时候才不会出现null值，但是很麻烦，要是父类再给个无参init方法，我们在重写时只需要重写这个，容器初始化调用的时候，子类继承了父类的带参init，会直接去父类给父类的类级变量赋值，由于父类的带参init调用了无参init(),因为我们使用的子类初始化，其就会调用子类重写的无参init()，非常完美的解决以上问题  
 
servlet的第二个api包，javax.servlet.http？  
为了更方便与http结合使用从而利用一些http的特性，提供了基于http编写servlet的类与接口，里面很多都覆盖了servlet的类型

javax.servlet.http有哪些接口和类？  
httpServlet类,httpservletrequest接口,httpservletresponse接口,httpsession接口,cookie    
httpServlet类这是个重要的类，继承自servlet接口的抽象类genericservlet ,提供了其他的方法与参数， httpservletrequest接口和httpservletresponse接口也都继承自servletrequest接口 和servletresponse接口意味着有一些相同的实现和一些自己的扩展，

	

	
	

















