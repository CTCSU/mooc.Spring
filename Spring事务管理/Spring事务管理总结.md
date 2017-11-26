&nbsp;//半角空格（英文）
&emsp;//全角空格（中文）

# Spring事务管理总结
本文对应慕课网上课程Spring事务管理，详情可查看：[点我](https://www.imooc.com/learn/478)  
## 1: 概念简析
* **事务**：逻辑的一组操作，要么一起成功，要么一起失败。
* **事务的特性**:  
    * 原子性:不可分割，要么都发生，要么都不发生
    * 一致性：数据完整性保持一致
    * 隔离性：不同的事务操作不能相互影响  (隔离级别)
    * 持久性：事务提交后，对数据的改变是永久的

**PlatformTransactionManager:**  
事务管理器类,不同持久化框架(例如JDBC,Hibernate,Mybatis|Mybatis好像使用和JDBC同一样的事务管理器实现类)对应不同实现类,注意具体使用时查询

**TransactionDefinition：**  
**数据库在查询数据时,由于种种原因会发生以下三种问题.**  
&emsp;&emsp;***脏读***  :读到还未修改的;  
&emsp;&emsp;***不可重复读***:在多次读同一记录过程中,另外事务提交修改,导致读取前后内容不同;  
&emsp;&emsp;***幻读***  :和不可重复读类似,不过另外一个事务插入而非修改  

</br>
</br>
**隔离级别 : 就是为了解决以上问题设定的事务相关的属性**  
READ_UNCOMMITED  
READ_COMMITTED  
REPEATABLE READ  
SERIALIZABLE      
以上四个级别一层层更严格,执行效率也越来越低.
最后的那个级别会导致所有事务呈线性执行

**传播行为：用来支持Spring的事务管理的属性，确定哪些操作会在被包含在同一个事务里**  
PROPAGATION_REQUIRED   支持当前事务，如果不存在，就创建一个  
PROPAGATION_SUPPORTS   具体查看相关文档  
PROPAGATION_MANDATORY  。。。。  
....

sql执行可以使用模板,也可以拿到sqlConnection之后自己执行,但是执行模板确实是最方便的方法


TransactionStatus：可以得到当前事务的相关属性

## 2: 编程式事务管理
1:配置事务管理器platformTransactionManager  
对于jdbc 需要datasourceTransactionManager 其中必须定义数据源属性dataSource,jdbc中是c3p0的连接池

2:事务管理的模板(注意和sql模板不同) transactionTemplate 事务管理模板,其属性就是上面对应的事务管理器. 

3:在需要事务管理的代码处使用内部匿名类来执行运行sql的代码,这样将所有的操作后台的sql都会放在同一个事务中.

## 3：声明式事务管理

1:首先还是要配置事务管理器

2:配置事务管理具体方法  

* 经典ProxyFactoryBean(使用动态代理的技术,用法已经渐渐废弃)  
需要配置TransactionProxyFactoryBean  属性包括target(标注哪个Bean需要事务管理) 和 事务管理器(上面配置的),在bean的配置中通过property来配置事务的传播行为和隔离级别. (只能对一个目标进行配置,就是那个target,不好用)

* 使用aop来进行事务管理  
使用基于AspectJ的Spring AOP来管理
xml配置:
配置通知 `<tx:advice>`
配置切面 `<aop:aspect>`,在切面中配置通知和切点.

* 注解配置:  
@Transactional即可配置;对需要的类配置,同时也可配置相关的隔离级别和传播行为属性.

相关的dao层配置请查看本系列文档关于Mybatis的总结





