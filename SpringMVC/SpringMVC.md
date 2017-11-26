#SpringMVC的总结
## MVC简介
Model-View-Control,MVC是一种架构模式,用于将数据-视图-逻辑分离  

### SpringMVC中基本概念
DispatcherServlet   就是一种特殊的Servlet,servlet实际上就是用来处理网站资源与请求的.**使用HandlerAdapter调用controller**

HandlerAdapter  掌握controller的关系,**实际上调用controller**
HandlerMapping  **url和controller之间的对应关系**

Controller     实际业务类,关心逻辑实现

HandlerInterceptor  **拦截器**,可以在controller运行之前拦截参数,进行相关的操作  

ModelAndView    Model具体表现
ViewResolver    资源与视图的对应关系

HandlerExecutionChain  调用过程  
一次SpringMVC访问过程  
request --> Dispatcher... --> HandlerMapping --> Controller  -->Handler -->ModelAndView --> ViewResolver -- >View  -->response

配置Meaven环境,略过不说H

###SpringMVC的配置
1:配置DispatcherServlet 
```
<servlet>
        <servlet-name>mvc-dispatcher</servlet-name>
        <!-- springmvc要用到的dispatcherservlet的配置-->
        <servlet-class> org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!-- DispatcherServlet对应的上下文配置， 默认为/WEB-INF/$servlet-name$-servlet.xml
         -->
        <init-param>
          <param-name>contextConfigLocation</param-name>
          <param-value>classpath*:mvc-dispatcher-servlet.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
```
2:配置servlet-mapping,确定哪些链接将被该servlet处理
```
<servlet-mapping>
        <servlet-name>mvc-dispatcher</servlet-name>
        <!-- mvc-dispatcher拦截所有的请求-->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```
  
</br>
3:配置dispatcherservlet需要的配置(在上面contextConfigLocation中配置的文件)
* 配置基于注解的扫描:`<context:annotation-config/>`  用来支持注解bean管理,比如@Required,@Autowired
* 配置默认扫描包:`<context:component-scan base-package>`
* 配置驱动注解`<mvc:annotion-driven>` 支持MVC相关注解解析,例如@RequetMapping,@Controller等
* 配置ViewResolver,用来映射返回结果与视图的关系,创建多个viewResolver对应不同的返回类型

4:创建相应的Controller,在controler的类上加上@Controlloer注解和RequestMapping,在相应的方法上加上requestMapping指定对应的htmlurl映射,可以对路径参数用@PathVariable注解绑定参数.

5:创建view,并使controller的返回结果能够通过viewResolver访问到对应的view.


以上是关于一个Spring MVC的基本框架
###以下为扩展内容
1:数据绑定.  
通过post方式,在requestmapping中,指定method为post  
直接指定方法的参数,SpringMVC会创建对象通过参数传进来.

2:SpringMVC的文件上传:Spring提供了方便的文件上传的组件.

3:使用JSON  
* 配置viewResolver
* 加入Jackson jar包
* 使controller返回相应对象,在对象的位置加上@RequestBody注解(也可返回RequestEntity<T> 这样的对象,不需要注解,spring自动转换.)



