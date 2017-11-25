# Spring入门篇总结：  
视频传送门:[Spring入门篇](http://www.imooc.com/learn/196)

______________________________________________________________________________________________________________________________________
该门课程主要从Spring的Bean的装配和AOP的简单使用这两个方面，结合简单的项目实例介绍了Spring起步的相关知识。
## 1 Spring是什么  
### 1.1 spring的概念和特性：
**关键字：Spring：开源框架，控制反转（IOC）和面向切面（AOP）**  
  Spring是一个开源框架，主要是为了解决项目开发中的紧耦合的问题，采用了控制反转和AOP技术简化了企业级应用的开发，让开发者能够更专注于业务的开发，减少了流程式的代码，简化了开发过程，所以受到开发人员的欢迎。  

  ***控制反转***：  
  在java开发中，如果要使用另外一个类的定义的功能，譬如在A类中需要调用B类的方法，那么一般的做法就是在A类中实例化一个B的对象,通过A.b.method()这样的方式来调用，那么这样造成了什么后果呢？那就是A强烈的依赖于B，这个时候就要求B中方法保持稳定，不能随意变动。这个时候B中的代码就被A给“限制”了，如果这个时候你要修改B中的方法，譬如将method()改为methodA()，那么同时也就要修改A中的代码以适应这个改变，这样的程序改动起来很大，也就是说扩展性很低。  
  控制反转就是为了解决这类问题所提出来的一种概念，主要就是通过一个容器来管理对象之间的依赖关系，Spring就提供了这样的一个容器来管理对象之间的依赖。就以上面的场景来说，A需要用到B中的功能，A只需要向容器提出我需要包含**功能的对象，容器就能给你一个这样的对象，至于这个对象到底是不是B？你管那么多呢？只要能用就行对吧。
  Spring中提供的这样的容器就是我们经常提到的ApplicationContext。  

  ***面向切面:***    
  在开发中，经常会遇到相同的模块，例如打印日志，安全管理这样的功能，可能每一个类都会有这样的需要。譬如我要记录每一个类的调用记录，使用参数等信息，这种功能k可以在每个类中加入相似的代码，直接打印出来就好。但是这种方法将辅助性的功能放到实际业务里去实现了，使每个方法承担的使命太多，如果能狗自动打印日志，不是更好吗？而这种通用的功能就称之为一个切面，面向切面的编程能够简化开发的实际代码，更好的关注于业务本身。

### 1.2 Spring的功能
IOC容器  
AOP  
集成其他的工具

## 2 Bean的装配
**面向接口编程:**  我对接口的理解实际上式接口就是一种规范，他定义了实现接口的类必须要实现的功能，因为java语言的多态性，在调用接口的地方也能使用实现了该接口的类来使用，能极大提高java语言的灵活性，而面向接口编程能更加方便地扩展整个代码。

### 2.1 基于xml的配置
1:xsd文件的引入：
xsd文件用于定义xml中关键字的解析，需要用到哪些模块就引入哪些模块
```
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
    xmlns:mvc="http://www.springframework.org/schema/mvc"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context 
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd">
```

2：bean定义
`<bean id="" class="">`

2：注入方式(ref表示bean引用，为bean的id|value表示注入值)
    构造器注入`<constructor-arg ref=""/>`或者`<constructor-arg value=""/>`
    set方法注入`<property ref=""/>`或者~

3：作用域（bean）：  
    **单例**：IOC容器只托管唯一的实例  
    **session**:每次http请求创建，当前session内有效  
    **request**；每次http请求创建一个实例，但前request内有效  
    **原型**：每次请求bean时都会实例化一个实例  
    单例和原型用的比较多。spring默认为单例。  
    在bean的定义中通过`scope`属性来指定

4：生命周期  
    定义  
    初始化  
    使用  
    销毁 
    我们可以在定义和销毁时增加自己需要的操作：可以实现接口或者在xml中定义，见下表：

| 实例类型 | 实现接口 | xml配置 |
| :--: | :-- | :-- |
| 初始化 | InitializingBean实现afterPropertiesSet方法 | init-method |
| 销毁 | DiposableBean实现destroy方法 | destroy-method |

5：自动装配
NO  
byName 根据bean的名字
byType  根据bean的类型那个
Constructor  类似byType

6：定义resource文件
通过classPath方式或者filePath方式比较常见

### 2.2 基于注解方式配置
1:定义需要扫描的位置
```
<context:component-scan base-package=""/>
```
2:定义Bean
@Component,@Repository,@Service,@Controller,@Bean;根据beanNameGenerator来定义bean的名称
3：装配
@Autowired,@Inject等

## 3 AOP
### 3.1 基本概念

* 切面：某个集成的功能  
* 连接点：程序执行时能够插入另外的程序的位置：比如说方法返回以后  
* 通知：切面要完成的功能  
* 切点:  定义在哪个连接点执行通知的功能是一种特殊的连接点，就是被使用的**连接点**  
**通知+所有切点 == 一个完整的切面**  


### 3.2 具体使用技巧
1：通知类型  
前置、后置、环绕、返回后、抛出异常后

2：Spring中AOP实现  

* SpringAOP  功能不如AspectJ强大，满足一般需要，常用
* AspectJ

3：Spring AOP的配置  
  `<aop:config>` 配置切面  
  `<aop:point-cut>`配置切点，使用'execution()'语法定义在**何处**切入类
  '<aop:before>'等配置通知，配置pointcut和具体方法

4：也可以使用proxy的方式来实现AOP，但是现在已经很少用了，主要不方便，这里也不再记录，以后有机会再详细描述下。

本来我写这篇文章的意思主要是对视频中老师提到的知识点做一个大体的总结，不知道为什么写着写着变成了对整个Spring的框架的描述了，继续编辑的话需要注意。















  

  

	
