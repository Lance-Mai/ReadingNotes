# chapter3 使用数据

在处理关系型数据库时，Java开发人员最常见的两种可选方案是：

1. JDBC
2. JPA

## JDBC

Spring对JDBC的支持主要归功于 JdbcTemplate类，此类可以通过一种特殊的方式，让开发人员在对关系型数据库进行SQL操作时避免使用JDBC那些繁琐的样板式代码

## Spring Data JPA

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

## 小结

- Spring的JdbcTemplate能够极大地简化JDBC的使用。
- 在我们需要知道数据库所生成的ID值时，可以组合使用PreparedStatementCreator和KeyHolder。
- 为了简化数据的插入，可以使用SimpleJdbcInsert。
- Spring Data JPA能够极大地简化JPA持久化，我们只需编写repository接口即可。