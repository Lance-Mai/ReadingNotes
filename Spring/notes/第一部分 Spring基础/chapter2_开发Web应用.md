# chapter2 开发Web应用

[toc]

- Spring MVC是基于注解的，通过像@RequestMapping、@GetMapping和@PostMapping这样的注解来启用请求处理方法的声明
- 大多数的请求处理方法最终会返回一个视图的逻辑名称，比如Thymeleaf模板，请求会转发到这样的视图上（同时会带有任意的模型数据）
- Spring MVC支持校验，这是通过Java Bean Validation API和Validation API的实现（如Hibernate Validator）完成的
- 对于没有模型数据和逻辑处理的HTTP GET请求，可以使用视图控制器
- 除了Thymeleaf之外，Spring支持各种视图方案，包括FreeMarker、Groovy Templates 和 Mustache

