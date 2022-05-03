# chapter1 Spring起步

[TOC]

- [chapter1 Spring起步](#chapter1-spring--)
  * [1. 什么是Spirng](#1----spirng)
  * [2. 自动装配&组件扫描](#2----------)
  * [3. Spring项目结构](#3-spring----)
  * [4. POM文件](#4-pom--)
  * [5. 引导类](#5----)
  * [6. 测试](#6---)
  * [7. 处理Web请求](#7---web--)

## 1. 什么是Spirng

- 容器（container），Spring应用上下文（Spring application context）
- 依赖注入（dependency injection，DI），将bean装配在一起

---

指导Spring应用上下文将bean装配在一起的方式

1. XML文件

    ```xml
    <bean id="inventoryService" class="com.example.InventoryService"/>
    <bean id="peoductService" class="com.example.ProductService">
    	<constructor-arg ref="inventoryService" />
    </bean>
    ```

    

2. 基于Java的配置

    ```java
    @Configuration
    public class ServiceConfiguration {
        @Bean
        public InventoryService inventoryService() {
            return new InventoryService();
        }
        @Bean
        public ProductService productService() {
            return new ProductService(inventoryService());
        }
    }
    ```

## 2. 自动装配&组件扫描

- 自动装配 autowiring
- 组件扫描 component scanning

## 3. Spring项目结构

- mvnw 和 mvnw.cmd：这是maven包装器（Wrapper）脚本。借助这些脚本，可以不需要安装maven就可以构建项目

- static：任意为浏览器提供服务的静态内容（图片、样式表、JavaScript等）

## 4. POM文件

- <packaging>war</packaging>：传统的web包应用打包方式，非常适合部署在传统的Java应用服务器上

- <packaging>jar</packaging>：本来用于打包库和较为少见的桌面UI应用，现在更适合用于部署在云上

- Springboot插件

    - 提供一个Maven Goal，允许我们使用Maven来运行应用
    - 它会确保依赖的所有库都会包含在可执行的文件中，并且能够保证他们在运行时 类路径是可用的
    - 它会在Jar中生成一个manifest文件，将引导类（启动类）声明为Jar的主类

    ```xml
    <build>
    	<plugins>
    		<plugin> ?--- Spring Boot插件
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-maven-plugin</artifactId>
    		</plugin>
    	</plugins>
    </build>
    ```

## 5. 引导类

```java
package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication ?--- Spring Boot应用
public class TacoCloudApplication {
	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args); ?--- 运行应用
	}
}
```

- @SpringBootApplication是一个组合注解，它组合了3个其他的注解
    - @SpringBootConfiguration：将该类声明为配置类，是@Configuration注解的特殊形式
    - @EnableAutoConfigration：启动SpringBoot的自动配置
    - @ComponentScan：启用组件扫描。这样我们就能通过像 @Component、@Controller、@Service这样的注解声明其他类，Spring会自动发现它们并将其注册为Spring应用上下文中的组件

## 6. 测试

@RunWith是JUnit的注解，提供一个测试运行器（runner）来指导JUnit如何进行测试。在本例中，为JUnit提供的是SpringRunner，这是Spring提供的一个测试运行器，它会创建测试运行所需的Spring上下文

```java
package tacos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) -- 使用Spring的运行器
@SpringBootTest -- Spring Boot测试
public class TacoCloudApplicationTests {
    @Test -- 测试方法
	public void contextLoads() {
	}
}
```

测试运行器的其他名称：

- SpringRunner是SpringJUnit4ClassRunner的别名，是在Spring4.3引用的，以便于移除对特定JUnit版本的关联

- @SpringBootTest会告诉JUnit在启动测试的时候要添加上SpringBoot的功能

## 7. 处理Web请求

- Spring自带web框架 Spring MVC，其核心是控制器（Controller）。控制器是处理请求并以某种方式进行信息响应的类。在面向浏览器的应用中，控制器会填充可选的数据模型并将请求传递给要给视图，以便于返回给浏览器的HTML
- @Controller：Spring的组件扫描并创建一个实例作为Spring应用上下文的bean
    - @Component、@Service、@Repository、@Controller的作用完全一样，只是描述的对象不同而已



