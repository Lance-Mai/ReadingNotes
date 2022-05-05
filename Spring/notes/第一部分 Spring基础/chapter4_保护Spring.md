# chapter4 保护Spring

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

- 使用转码后的密码存入数据库中，而且转码后的密码永远不会被解码（hash是不可逆的）

## 防止跨站请求伪造

- 跨站请求伪造（Cross-Site Request Forgery， CSRF）

![image-20220505220816523](https://cdn.jsdelivr.net/gh/Lance-Mai/MyPictureBed/images/image-2022/image-20220505220816523.png)



- 为了防止这类攻击，应用可以在展现表单的时候生成一个CSRF token，放到隐藏域中，然后临时存储起来，以便后续在服务器上使用。用户在提交表单时，将token和其他表单数据一起发送至服务器。服务器会对请求进行拦截，将接收到的token与最初生成的token进行对比（恶意网站不知道服务器所生成的token）

![image-20220505221532758](https://cdn.jsdelivr.net/gh/Lance-Mai/MyPictureBed/images/image-2022/image-20220505221532758.png)