# chapter1 Spring起步

[TOC]

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

    

