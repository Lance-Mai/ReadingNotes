# chapter5 使用配置属性

Spring中有两种不同的配置

1. bean装配：声明在spring应用上下文中创建哪些应用组件以及它们之间如何相互注入的配置
2. 属性注入：设置spring应用上下文中bean的值的配置

## 1. 如何理解Spring的环境抽象

Spring环境抽象是各种配置属性的一站式服务，会拉取多个属性源，包括：

- JVM系统属性
- 操作系统环境变量
- 命令行参数
- 应用属性配置文件

![image-20220505222336377](https://cdn.jsdelivr.net/gh/Lance-Mai/MyPictureBed/images/image-2022/image-20220505222336377.png)

配置属性的元数据完全是可选的，其并不会妨碍配置属性的运行，但是元数据对于为配置属性提供一个最小化文档非常有用。

## 2. 使用profile进行配置

定义特定profile相关属性的方式：

- 第一种：创建另外一个属性文件，其中只包含用于特定环境的属性，文件名遵守如下约定：application-{profile名}.yml 或 application-{profile名}.properties，例如application-prod.properties文件

- 第二种：只适用于YAML配置：它会将特定profile的属性和非profile的属性多放在 application.yml中，它们之间使用3个中划线 `---`进行分割，并使用spring.profiles属性来命名profile

![image-20220505223938767](https://cdn.jsdelivr.net/gh/Lance-Mai/MyPictureBed/images/image-2022/image-20220505223938767.png)

### 激活profile

![image-20220505224809058](https://cdn.jsdelivr.net/gh/Lance-Mai/MyPictureBed/images/image-2022/image-20220505224809058.png)

![image-20220505224829190](https://cdn.jsdelivr.net/gh/Lance-Mai/MyPictureBed/images/image-2022/image-20220505224922422.png)

![image-20220505224903203](https://cdn.jsdelivr.net/gh/Lance-Mai/MyPictureBed/images/image-2022/image-20220505224903203.png)

![image-20220505224845235](https://cdn.jsdelivr.net/gh/Lance-Mai/MyPictureBed/images/image-2022/image-20220505224845235.png)

### 使用profile条件化地创建bean

- 使用 **@profile** 注解

![image-20220505225101049](https://cdn.jsdelivr.net/gh/Lance-Mai/MyPictureBed/images/image-2022/image-20220505225101049.png)

![image-20220505225129988](https://cdn.jsdelivr.net/gh/Lance-Mai/MyPictureBed/images/image-2022/image-20220505225129988.png)

![image-20220505225222419](https://cdn.jsdelivr.net/gh/Lance-Mai/MyPictureBed/images/image-2022/image-20220505225222419.png)