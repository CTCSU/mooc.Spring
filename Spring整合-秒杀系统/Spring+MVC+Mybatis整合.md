#Spring+MVC+Mybatis整合
### 什么是秒杀业务
网站售卖某产品时,规定在某个日期开始售卖限量的产品,最典型的比如小米的开售;这种情况下,可能有很多用户对同一产品在同一时间请求购买,并发数特别高,所以对数据库和网络的设计要求比较高.

### 秒杀系统的业务分析
秒杀系统最关键的部分是对库存的访问与修改,可能存在同一时间对数据库里的同一字段大量的访问,如何保证查询的时间比较短,让尽可能多的用户尽快访问,是此类业务的关键.

### 分层
- Dao层:  数据层,主要是对数据的访问;
- Service层:  业务层,封装主要的业务逻辑
    - exception层:  定义所有exception
- Web层:  展示给用用户,即前台页面

***
注意要多写注释
***

### Dao层:
1. 使用sql创建相关的数据库和表;
2. 创建实体,一般对应数据库的一张表;
3. 创建Dao层接口,定义预先定义的方法;
4. 使用Mybatis实现Dao接口,一般来说使用Mapper中的namespace对应到对应接口,Mybatis会自动创建其实现类;  
    [mapper namespace]
    [resultMap]
    [seelct id]  
    具体可参考系列文档中关于mybatis的总结
5. 写好Mybatis的xml配置文件,启用Mybatis的功能:  
     mybatis-config.xml的基本配置:  
```
     <configuration>
    <!-- 配置全局属性 -->  
    <settings>  
        <!-- 使用jdbc的getGeneratedKeys获取数据库自增主键   -->
        <setting name="useGeneratedKeys" value="true"/>
        <!-- 使用列别名替换列名 -->
        <setting name="useColumnLabel" value="true"/>
        <!-- 开启驼峰命名转换  下划线命名到驼峰命名的转换-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>     
    </settings>
    </configuration>
```

(以下序号应当从6开始)  

6. Spring整合Mybatis:spring-dao.xml  
    1. 引入property文件;
    2. 创建数据源-一般是c3p0
    3. SqlSessionFactory的配置(属性:**连接池**,***配置文件-全局文件指定****)
    4. 配置扫描接口,指定**扫描哪些包**下的Dao接口

7. 单元测试

### Service层:
##### 1.Service层关注业务怎么实现,一开始级应该设计好  
从"使用者"的角度设计接口:  

- 方法定义粒度  
- 参数要好处理         
- 返回类型
- 写好注释


##### 2.业务代码,爱咋写咋写,按层次即可

##### 3.通过Spring IOC管理所有的service层代码
使用注解的话,@Service,@Bean,@Autowired等注解实现bean的托管和依赖注入  
以下为Spring注解开启的示例文件.
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
    xmlns:aop="http://www.springframework.org/shcema/aop"
    xmlns:tx="http://www.springframework.org/schema/tx"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop-4.0.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd">
        <!-- 扫描service报下所有使用注解的类型 -->
        <context:component-scan base-package="com.ct.maven.SecKill.service"></context:component-scan>
       
        <!-- 配置事务管理器 -->
        <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
            <!-- 注入数据库连接池 -->
            <property name="dataSource" ref="dataSource"></property>
        </bean>
        
        <tx:annotation-driven transaction-manager="transactionManager"/>
</beans>
```

##### 4.使用集成测试

### Web层:
[restful] [bootStrap+jquery] [SpringMVC] 
##### 1:restful接口设计,实际上是uri的定义规范,资源的状态或者资源状态的转移;Spring注解很方便地和resultful适配
##### 2:编写controller,详情可参见SpringMVC的总结.
##### 3:配置mvc
 - 开启spring mvc注解 mvc:annotion-driven
 - 静态资源处理
 - viewResolver配置
 - 扫描web相关的bean;实际上,关于bean的定义可以在统一的文件包中,而不必要分在三个层中进行配置.

### Dao+Web+Service整合
 - 配置dispatcher(servlet)
 - 配置contextConfiglocation(这里就是所有的层的整合,当spring读取到所有的xml文件后,便可以互相调用和整合,因为实在一个完整的spring容器中)
 - 配置servlet-mapping

---
#秒杀业务的深入,高并发的优化
#### 三种途径
1:前端cdn访问  
2:redis缓存处理;
redis有windows版本可以使用  
3:并发访问,通过存储过程将一系列的操作一起进行,降低因为网络延迟导致的行级锁










