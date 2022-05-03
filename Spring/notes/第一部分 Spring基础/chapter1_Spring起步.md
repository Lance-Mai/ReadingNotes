# chapter1 Spring��

[TOC]

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

    

