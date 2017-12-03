# MyBatis总结
## JDBC写法
```
//sql:
String sql = "select * from user";
//加载驱动:
Class.forName("com.mysql.jdbc.Driver");
//根据参数创建连接
Connection conn = DriverManager.getConnection(url,username,password);
//创建PreparedStatement
PreparedStatement statement = conn.prepareStatement(sql);
//执行sql,并使用resultSet接收结果
ResultSet rs =  statement.executeQuery();
//将rs里的所有值取出来
try{
while(rs.next()){
    String ... = rs.getString(1)/rs.getString("userName");       //从结果中根据列名或者列的序号取值.
}
}catch(SqlException ex){

    }finally{
        //必须要手动释放连接
        conn.close();
    }
//

```
以上即是一个简单的jdbc查询数据库的写法.
有什么问题呢?
第一是sql的写法,如果sql过长,参数过多,拼接起来很麻烦,不直观,并且sql很难查看;
第二是Connectione的创建和销毁需要手动控制,而这些应该是业务无关代码.


## Mybatis主要概念
ORM框架是一种数据操作的框架,常见的比如JDBC,Hibernate,当然,Mybatis也是其中的一种.  
相比于JDBC,Mybatis的优势在于能将Sql集中配置;并且能和Spring集成;将connection交给Spring托管,不需要开发者自己操作;同时也能方便进行AOP的开发.  
而和hibernate对比,Mybatis则比较容易学,没有hibernate那么高的门槛.

## 如何配置Mybatis
##### 1.配置Mybatis的配置文件Configuration.xml
```

```

##### 2. 读取xml,创建sqlsession 
```
        //通过配置文件获取数据库连接信息
        Reader reader = Resources.getResourceAsReader("com/chentao/MicroMessage/config/mybatis-config.xml");
        
        //通过配置信息构建一个SqlSessionFactory
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        
        //通过sqlsessionfactory打开session
        SqlSession sqlSession = sessionFactory.openSession();
        
        return sqlSession;
```

#####  3.配置与Sql相关的XML文件
######  3.1  定义namespace    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  --namespace定义了xml文件的域,和mybatis接口相匹配,也是为了标志在xml文件中定义的属性等
######  3.2  定义resultMap,用来简单接受返回对象(当然也可以不用定义,返回时)mybatis使用约定来返回对象)
######  3.3  在xml中定义sql,通过id对应方法名称,在java中调用

######  4.在Configuration.xml中将第三步配置的文件通过mapper属性引入文内存中读取
######   5.在Java中使用接口来调用sql写就的业务

#####  4.关于代码中参数的使用,可以传入一个map,可以传入对象,也可以传入多个对象,在(需要接口中通过注解定义在sql中使用的参数名)

#####  5.关于一对多配置的使用:
###### 5.1 在resultMap中引入其他的resultMap
###### 5.2 在sql中使用join这样的方式可以查出一对多的对象,
###### 5.3 如果在返回结果中字段名称相同,则需要使用As指定别名,并在resultMap的属性中体现出来. 
***

## 概念深入了解(加强版)
### 接口式编程
通过namespace的包名和相应接口的包名与类名进行匹配,可以通过调用接口的方法直接使用sql的逻辑.
mybatis实现这一特性的方法是动态代理.

###分页:
根据每页的条数,和需要查询的页数可以加载整个页面的数据;
简单逻辑是从数据库拿到总条数,然后计算出总页数,然后通过数据查询出第n到m条数据,使用limit关键字(mysql中)即可以拿到需要查询的数据.


### Mybatis拦截器
#### 实现:
1:实现mybatis的Inteceptor接口  
在实现类上使用注解:Intercepts来定义需要拦截哪种类型的方法.  
实现plugin方法和intercept方法;  

2:在plugin中根据注解和传进来的对象确定是否拦截该对象.

3:在intercept方法中确定是否拦截对应方法,并拿到其中参数,包括sql,增加自己的逻辑.



