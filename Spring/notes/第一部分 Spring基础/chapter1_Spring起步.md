# chapter1 Spring��

[TOC]

- [chapter1 Spring��](#chapter1-spring--)
  * [1. ʲô��Spirng](#1----spirng)
  * [2. �Զ�װ��&���ɨ��](#2----------)
  * [3. Spring��Ŀ�ṹ](#3-spring----)
  * [4. POM�ļ�](#4-pom--)
  * [5. ������](#5----)
  * [6. ����](#6---)
  * [7. ����Web����](#7---web--)

## 1. ʲô��Spirng

- ������container����SpringӦ�������ģ�Spring application context��
- ����ע�루dependency injection��DI������beanװ����һ��

---

ָ��SpringӦ�������Ľ�beanװ����һ��ķ�ʽ

1. XML�ļ�

    ```xml
    <bean id="inventoryService" class="com.example.InventoryService"/>
    <bean id="peoductService" class="com.example.ProductService">
    	<constructor-arg ref="inventoryService" />
    </bean>
    ```

    

2. ����Java������

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

## 2. �Զ�װ��&���ɨ��

- �Զ�װ�� autowiring
- ���ɨ�� component scanning

## 3. Spring��Ŀ�ṹ

- mvnw �� mvnw.cmd������maven��װ����Wrapper���ű���������Щ�ű������Բ���Ҫ��װmaven�Ϳ��Թ�����Ŀ

- static������Ϊ������ṩ����ľ�̬���ݣ�ͼƬ����ʽ��JavaScript�ȣ�

## 4. POM�ļ�

- <packaging>war</packaging>����ͳ��web��Ӧ�ô����ʽ���ǳ��ʺϲ����ڴ�ͳ��JavaӦ�÷�������

- <packaging>jar</packaging>���������ڴ����ͽ�Ϊ�ټ�������UIӦ�ã����ڸ��ʺ����ڲ���������

- Springboot���

    - �ṩһ��Maven Goal����������ʹ��Maven������Ӧ��
    - ����ȷ�����������пⶼ������ڿ�ִ�е��ļ��У������ܹ���֤����������ʱ ��·���ǿ��õ�
    - ������Jar������һ��manifest�ļ����������ࣨ�����ࣩ����ΪJar������

    ```xml
    <build>
    	<plugins>
    		<plugin> ?--- Spring Boot���
    			<groupId>org.springframework.boot</groupId>
    			<artifactId>spring-boot-maven-plugin</artifactId>
    		</plugin>
    	</plugins>
    </build>
    ```

## 5. ������

```java
package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication ?--- Spring BootӦ��
public class TacoCloudApplication {
	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args); ?--- ����Ӧ��
	}
}
```

- @SpringBootApplication��һ�����ע�⣬�������3��������ע��
    - @SpringBootConfiguration������������Ϊ�����࣬��@Configurationע���������ʽ
    - @EnableAutoConfigration������SpringBoot���Զ�����
    - @ComponentScan���������ɨ�衣�������Ǿ���ͨ���� @Component��@Controller��@Service������ע�����������࣬Spring���Զ��������ǲ�����ע��ΪSpringӦ���������е����

## 6. ����

@RunWith��JUnit��ע�⣬�ṩһ��������������runner����ָ��JUnit��ν��в��ԡ��ڱ����У�ΪJUnit�ṩ����SpringRunner������Spring�ṩ��һ�����������������ᴴ���������������Spring������

```java
package tacos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) -- ʹ��Spring��������
@SpringBootTest -- Spring Boot����
public class TacoCloudApplicationTests {
    @Test -- ���Է���
	public void contextLoads() {
	}
}
```

�������������������ƣ�

- SpringRunner��SpringJUnit4ClassRunner�ı���������Spring4.3���õģ��Ա����Ƴ����ض�JUnit�汾�Ĺ���

- @SpringBootTest�����JUnit���������Ե�ʱ��Ҫ�����SpringBoot�Ĺ���

## 7. ����Web����

- Spring�Դ�web��� Spring MVC��������ǿ�������Controller�����������Ǵ���������ĳ�ַ�ʽ������Ϣ��Ӧ���ࡣ�������������Ӧ���У�������������ѡ������ģ�Ͳ������󴫵ݸ�Ҫ����ͼ���Ա��ڷ��ظ��������HTML
- @Controller��Spring�����ɨ�貢����һ��ʵ����ΪSpringӦ�������ĵ�bean
    - @Component��@Service��@Repository��@Controller��������ȫһ����ֻ�������Ķ���ͬ����



