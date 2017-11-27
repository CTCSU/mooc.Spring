#Spring拦截器总结:
Spring过滤器WebFilter可以配置中文过滤

####拦截器实现步骤
1:实现拦截器  
编写拦截器类实现HandlerInterceptor接口  
实现接口中的默认方法.接受的参数有HttpServletRequet,HttpServletResponse,Object是拦截请求的目标的对象,即Controller

2:将拦截器注册到SpringMVC中去  
`<mvc:interceptor class="">`即可

3:配置拦截器的拦截规则
`<mvc:mapping>` 配置规则


####拦截器说明
拦截器中的方法:依次调用
preHandle;  
postHandle  ModelAndView可以修改返回的view.
afterCompletion;  视图被显示之后,用于资源的销毁

每个都有返回值,false和true;

多个拦截器依次执行

***
#### 其他实现
实现WebRequestInterceptor接口,该接口的方法没有返回值,不能终止请求,功能不齐全.写法不变.

拦截器使用场景:  
乱码,登陆


拦截器和过滤器的区别
过滤器基于与Servlet,基于**回调函数**,功能更强大;
拦截器基于Spring框架,基于**反射机制**,只能对方法进行处理..

拦截器和过滤器都能实现对请求的预先处理,Spring中优先使用拦截器,更方便.
