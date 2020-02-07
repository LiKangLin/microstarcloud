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


【2020-2-7工作汇报】李康林
1、交易风险实时决策系统培训心得：
(1)当前公司使用的实时风控系统的现状以及不足，了解外界实时风控系统的现状
(2)明确核心的功能需求，从而确定具体的技术实施方案
(3)了解方案中涉及到的指标变量实时加工、规则制定以及实时决策等设计逻辑
2、搭建Rest微服务工程，实现消费者通过RestTemplate远程访问HTTP服务的功能