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



20200212
Hystrix 针对服务端的断路器
服务雪崩：多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C，
微服务B和微服务C又调用其它的微服务，这就是所谓的"扇出"。如果扇出的链
路上某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越
来越多的系统资源，进而引起系统崩溃，所谓的"雪崩效应"

对于高流量的应用来说，单一的后端依赖可能会导致所有服务器上的所有资源
都在几秒内饱和。比失败更糟糕的是，这些应用程序还可能导致服务之间的延
迟增加，备份队列、线程和其它系统资源紧张，导致整个系统发生更多的级联
‘故障， 这些都表示需要对故障和延迟进行隔离和管理，以便单个依赖关系的
失败，不能取消整个应用程序或系统


服务熔断---针对于服务端
熔断机制是应对雪崩效应的一种微服务链路保护机制。
当扇出链路的某个微服务不可用或者响应时间太长时，会进行服务的降级，进而
熔断该节点微服务的调用，快速返回"错误"的响应信息。

服务降级---针对于客户端
当服务器压力剧增的情况下，根据实际业务情况及流量，对一些服务和页面有策略
的不处理或换种简单的方式处理，从而释放服务器资源以保证核心交易正常运作或
高效运作。

运行过程中，当服务端突然down了，但是我们在客户端做了降级处理，，让客户端
在服务端不可用时也会获得提示信息而不会挂起耗死服务器。

服务监控---豪猪界面
除了隔离依赖服务的调用以外，Hystrix还提供了准实时的调用监控
（Hystrix Dashboard），Hystrix会持续地记录所有通过Hystrix
发起的请求的执行信息，并以统计报表和图形的形式展示给用户，
包括每秒执行多少请求多少成功，多少失败等。Netflix通过
hystrix-metrics-event-stream项目
实现了对以上指标的监控。Spring Cloud也提供了Hystrix Dashboard的
整合，对监控内容转化成可视化界面。


zuul---路由网关

Zuul包含了对请求的路由和过滤两个最主要的功能：
其中路由功能负责将外部请求转发到具体的微服务实例上，是实现外部访问统一入口的基础而过滤器功能则负责对请求的处理过程进行干预，是实现请求校验、服务聚合等功能的基础.

Zuul和Eureka进行整合，将Zuul自身注册为Eureka服务治理下的应用，同时从Eureka中获得其他微服务的消息，也即以后的访问微服务都是通过Zuul跳转后获得。

    注意：Zuul服务最终还是会注册进Eureka

提供=代理+路由+过滤三大功能

访问地址：
Ip+端口/要访问的微服务名称/rest风格地址
原路径
http://myzuul.com:9527/mydept/dept/get/1
加入zuul网关后的路径
http://myzuul.com:9527/microstarcloud-dept/dept/get/2

在zuul中添加前缀/Lrunner,此时的访问地址为：
http://myzuul.com:9527/Lrunner/mydept/dept/get/1


SpringCloud_config 分布式配置中心
为微服务架构中的微服务提供集中化的外部配置支持，配置服务端为各个
不同微服务应用的所有环境提供了一个中心化的外部配置。

SpringCloud Config分为服务端和客户端两部分
：服务端也称为分布式配置中心，它是一个独立的微服务应用，用来连接配
置服务器并为客户端提供获取配置信息，加解密信息等访问接口
：客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置
内容，并在启动的时候从配置中心获取和加载配置信息。
配置服务器默认采用git来存储配置信息，这样就有助于对环境配置进行版
本管理，并且可以通过git客户端工具来方便的管理和访问配置内容

不同环境不同配置，动态化的配置更新，分环境部署入dev/test/prod等环境
当配置发生变动时，服务不需要重启即可感知到配置的变化并应用新的配置
将配置信息以rest接口的形式暴露



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

【2020-2-12工作汇报】李康林
1、学习性能测试培训知识
2、学习分布式架构知识，接入hystrix和zuul组件