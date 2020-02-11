时间：20200206
一、创建父工程---相当于一个抽象父类
    打开IDEA，创建一个maven project，指定group ID以及对应属性
    <groupId>com.star.microserver</groupId>
    <artifactId>microservicecloud</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    注意打包方式为pom

    以及在pom文件中指定cloud以及boot的微服务版本、
    Junit、SQL数据库、以及log版本

    <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <maven.compiler.source>1.8</maven.compiler.source>
            <maven.compiler.target>1.8</maven.compiler.target>
            <junit.version>4.12</junit.version>
            <log4j.version>1.2.17</log4j.version>
            <lombok.version>1.16.18</lombok.version>
        </properties>

        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-dependencies</artifactId>
                    <version>Dalston.SR1</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-dependencies</artifactId>
                    <version>1.5.9.RELEASE</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <version>5.0.4</version>
                </dependency>
                <dependency>
                    <groupId>com.alibaba</groupId>
                    <artifactId>druid</artifactId>
                    <version>1.0.31</version>
                </dependency>
                <dependency>
                    <groupId>org.mybatis.spring.boot</groupId>
                    <artifactId>mybatis-spring-boot-starter</artifactId>
                    <version>1.3.0</version>
                </dependency>
                <dependency>
                    <groupId>ch.qos.logback</groupId>
                    <artifactId>logback-core</artifactId>
                    <version>1.2.3</version>
                </dependency>
                <dependency>
                    <groupId>junit</groupId>
                    <artifactId>junit</artifactId>
                    <version>${junit.version}</version>
                    <scope>test</scope>
                </dependency>
                <dependency>
                    <groupId>log4j</groupId>
                    <artifactId>log4j</artifactId>
                    <version>${log4j.version}</version>
                </dependency>
            </dependencies>
        </dependencyManagement>


二、创建microstarcloud-api
   在microstarcloud工程上创建新的module，对应的maven工程，此时它的父类工程是microstarcloud
   <parent>
       <artifactId>microservicecloud</artifactId>
       <groupId>com.star.microserver</groupId>
       <version>1.0-SNAPSHOT</version>
   </parent>
   <modelVersion>4.0.0</modelVersion>
   <packaging>jar</packaging>    //注意打包方式为jar
   <artifactId>microstarcloud-api</artifactId>


   父工程中自动添加了
   <modules>
      <module>microstarcloud-api</module>
   </modules>

   然后在api工程中添加entities实体类

三、创建microstar-provider-dept-8001
   8001为端口
   代码流程为：
   1、新建module工程microstar-provider-dept-8001
   2、新建pom.xml文件
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <parent>
           <artifactId>microservicecloud</artifactId>
           <groupId>com.star.microserver</groupId>
           <version>1.0-SNAPSHOT</version>
       </parent>
       <modelVersion>4.0.0</modelVersion>

       <artifactId>microstarprovider-dept-8001</artifactId>

       <dependencies>
           <dependency><!-- 引入自己定义的api通用包，可以使用Dept部门Entity -->
               <groupId>com.star.microserver</groupId>
               <artifactId>microstarcloud-api</artifactId>
               <version>${project.version}</version>
           </dependency>
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
           </dependency>
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
           </dependency>
           <dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>druid</artifactId>
           </dependency>
           <dependency>
               <groupId>ch.qos.logback</groupId>
               <artifactId>logback-core</artifactId>
           </dependency>
           <dependency>
               <groupId>org.mybatis.spring.boot</groupId>
               <artifactId>mybatis-spring-boot-starter</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-jetty</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
           </dependency>
           <!-- 修改后立即生效，热部署 -->
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>springloaded</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-devtools</artifactId>
           </dependency>
       </dependencies>
   </project>

   3、新建application.yml启动配置文件
   对应的添加mybtis配置
   server:
     port: 8001

   mybatis:
     config-location: classpath:mybatis/mybatis.cfg.xml        # mybatis配置文件所在路径
     ##扫描包
     type-aliases-package: com.microstar.springcloud.entities    # 所有Entity别名类所在包
     ##mapper文件的位置
     mapper-locations:
     - classpath:mybatis/mapper/**/*.xml                       # mapper映射文件

   spring:
      application:
       name: microstarcloud-dept
      datasource:
       type: com.alibaba.druid.pool.DruidDataSource            # 当前数据源操作类型
       driver-class-name: org.gjt.mm.mysql.Driver              # mysql驱动包
       url: jdbc:mysql://localhost:3306/cloudDB01              # 数据库名称
       username: root
       password: 1234
       dbcp2:
         min-idle: 5                                           # 数据库连接池的最小维持连接数
         initial-size: 5                                       # 初始化连接数
         max-total: 5                                          # 最大连接数
         max-wait-millis: 200                                  # 等待连接获取的最大超时时间



   4、工程src/main/resources目录下新建mybatis文件夹后新建mybatis.cfg.xml文件

   5、MySQL创建部门数据库脚本

   6、编写对应的接口类
       DeptDao部门接口，与entities实体类对应

   7、工程src/main/resources/mybatis目录下新建mapper文件夹后再建DeptMapper.xml

   8、DeptService部门服务接口  service接口

   9、DeptServiceImpl部门服务接口实现类  service实现类

   10、编写对应的controller类

   11、新增启动类

   12、访问测试



20200207
一、
RestTemplate提供了多种便捷访问远程Http服务的方法，
是一种简单便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具集

官网地址

https://docs.spring.io/spring-framework/docs/4.3.7.RELEASE/javadoc-api/org/springframework/web/client/RestTemplate.html


使用
使用restTemplate访问restful接口非常的简单粗暴无脑。
(url, requestMap, ResponseBean.class)这三个参数分别代表
REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。

20200210
新增ribbon组件，针对客户端的LB软负载均衡组件
负载均衡的算法，轮询、随机、访问加权的算法
通过IRule接口，或者可自定义负载均衡算法
@Bean
public IRule myRule(){
   return new RandomRule();可以调整为随机算法
}

202020211
Feign组件的学习
创建一个接口和一个注解
Feign是一个声明式WebService客户端，使用Feign能让编写Web Service客户
端更加简单，它的使用方式是定义一个接口，然后在上面添加注解，同时也支持
JAX-RS标准的注解。Feign也支持可拔插式的编码器和解码器，Spring Cloud对
Feign进行了封装，使其支持了Spring MVC标准注解和HttpMessageConverts。
Feign可以与Eureka和Ribbon组合使用以支持负载均衡。

feign面向接口编程，而ribbon是面向微服务名称的负载均衡
1、微服务名称获得调用地址
2、就是通过接口+注解，获得我们的调用服务

Feign能干什么
Feign旨在使编写Java Http客户端变得更容易
前面在使用Ribbbon+RestTemplate时，利用RestTemplate
对http请求的封装处理，形成了一套模板化的调用方法。但是在实际开发中，由于对服务
依赖的调用可能不止一处，往往一个接口会被多次调用，所以通常都会针对每个微服务自行封
装一些客户端类来包装这些依赖服务的调用。所以，Feign在此基础上做了进一步的封装，由它来
帮助我们定义和实现依赖服务接口的定义。在Feign的实现下，我们只需创建一个接口并使用注
解的方式来配置它(以前是Dao接口上面标注Mapper注解，现在是一个微服务接口上面标注一个
Feign注解即可)，即可完成对服务提供方的接口绑定，简化了使用Spring cloud Ribbon时，自动
封装服务调用客户端的开发量。

操作步骤
1、在api公共类中添加service，加上@FeignClient接口
@FeignClient(value = "MICROSTARCLOUD-DEPT")
  public interface DeptClientService
  {
      @RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
      public Dept get(@PathVariable("id") long id);

      @RequestMapping(value = "/dept/list", method = RequestMethod.GET)
      public List<Dept> list();

      @RequestMapping(value = "/dept/add", method = RequestMethod.POST)
      public boolean add(Dept dept);
  }

2、在Feign的消费工程中使用service进行调用
public class DeptController_Feign
{
    @Autowired
    private DeptClientService service;

    @RequestMapping(value = "/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id)
    {
        return this.service.get(id);
    }

    @RequestMapping(value = "/consumer/dept/list")
    public List<Dept> list()
    {
        return this.service.list();
    }

    @RequestMapping(value = "/consumer/dept/add")
    public Object add(Dept dept)
    {
        return this.service.add(dept);
    }
}










【2020-2-7工作汇报】李康林
1、交易风险实时决策系统培训心得：
(1)当前公司使用的实时风控系统的现状以及不足，了解外界实时风控系统的现状
(2)明确核心的功能需求，从而确定具体的技术实施方案
(3)了解方案中涉及到的指标变量实时加工、规则制定以及实时决策等设计逻辑
2、搭建Rest微服务工程，实现消费者通过RestTemplate远程访问HTTP服务的功能

【2020-2-11工作汇报】李康林
1、学习信用卡利息培训
2、读架构真经第十二章
3、接入Feign组件，实现负载均衡