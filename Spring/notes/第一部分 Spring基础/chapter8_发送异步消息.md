# chapter8 发送异步消息

异步消息是一个应用程序向另一个应用程序间接发送消息的一种方式，这种间接性能够为进行通信的应用带来**更松散的耦合**和**更大的可伸缩性**。

## Spring的三种异步消息方案

1. Java消息服务（Java Message Service，JMS）
2. RabbitMQ和高级消息队列协议（Advanced Message Queueing Protocol）
3. Apache Kafka

### 1、JMS

Spring基于模板的抽象为JMS提供支持，即 JmsTemplate。

- 在发送和消息之前，首先需要一个消息代理（broker），其能够在消息的生产者和消费者之间传递消息

#### 搭建JMS环境

```xml
<!-- 如果选择使用Apache ActiveMQ -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-activemq</artifactId>
</dependency>

<!-- 如果选择使用ActiveMQ Artemis -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-artemis</artifactId>
</dependency>
```

![image-20220510235028016](C:%5CUsers%5CAdministrator%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20220510235028016.png)

![image-20220510235137598](C:%5CUsers%5CAdministrator%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20220510235137598.png)

---

### 2、RabbitMQ

- RabbitMQ可以说是AMQP最杰出的实现，他提供了比JMS更高级的消息路由策略。JMS消息使用目的地名称来寻址，接收者要从这里检索消息；而AMQP消息使用Exchange和routing key来寻址，这样消息就与消息者要监听的队列解耦了。

![image-20220511000043640](C:%5CUsers%5CAdministrator%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20220511000043640.png)

![image-20220511000215501](C:%5CUsers%5CAdministrator%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20220511000215501.png)

![image-20220511000240930](C:%5CUsers%5CAdministrator%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20220511000240930.png)

![image-20220511000603857](C:%5CUsers%5CAdministrator%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20220511000603857.png)

![image-20220511000622016](C:%5CUsers%5CAdministrator%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20220511000622016.png)

![image-20220511000725257](C:%5CUsers%5CAdministrator%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20220511000725257.png)

---

### 3、Kafka

- Kafka设计为集群运行，可以实现很强的可扩展性。通过将主题在集群的所有实例上进行分区（partition），使其能够具有更强的弹性。RabbitMQ主要处理Exchange中的队列，而Kafka仅使用主题实现消息的发布/订阅。

- Kafka主题会复制到集群的所有代理上。集群中的每个节点都会担任一个或多个主题的首领（leader），负责该主题的数据并将其复制到集群的其他节点上。

#### Kafka架构原理解析

http://www.dockone.io/article/9956

##### 关键概念

1. Broker：一台Kafka机器就是一个Broker。一个集群有多个Broker组成，一个Broker可以容纳多个Topic
2. Producer：消息生产者，向Kafka Broker发消息的客户端
3. Consumer：消息消费者，从Kafka Broker取消息的客户端
4. Consumer Group：消费者组（CG），消费者组内每个消费者负责消费不同分区的数据，提高消费能力。一个分区只能由组内一个消费者消费，消费者组之间互不影响。所有的消费者都属于某个消费者组，即消费者组是逻辑上的一个订阅者
5. Topic：可以理解为一个队列，Topic将消息进行分类，生产者和消费者面向的是同一个Topic
6. Partition：为了提高扩展性和并发能力，一个非常大的Topic可以分布到多个Broker（服务器）上，一个Topic可以分成多个Partition，每个Partition是一个**有序的队列**
7. Replica：副本，实现备份的功能。一个Topic的每个分区都有若干个副本，一个Leader和若干个Follower。
8. Leader：每个分区多个副本的“主”副本，生产者发送数据的对象，以及消费者消费数据的对象，都是Leader
9. Follower：每个分区多个副本的“从”副本，实时从Leader中同步数据，保持和Leader数据的同步。Leader发生故障时，某个Follower还会成为新的Leader
10. Offset：消费者消费的位置信息，监控数据消费到什么位置。当消费者挂掉后重新恢复，可以从消费位置继续消费
11. Zookeeper: Kafka集群能够正常工作，需要依赖ZK来帮助Kafka存储和管理集群信息

![image-20220511002653982](C:%5CUsers%5CAdministrator%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20220511002653982.png)

##### 定义

- Kafka是一个分布式的基于发布/订阅模式的消息队列（Message Queue），主要应用于大数据实时处理领域

##### **消息队列的好处是什么？**

- 解耦：允许我们独立的扩展或者修改队列两边的处理过程
- 可恢复性：即使一个处理消息的进程挂掉，加入队列中的消息【未被消费】仍然可以在系统恢复后被处理
- 缓冲：有助于解决生产消息的消费消息的处理速度不一致的情况
- 灵活性&峰值处理能力：不会因为突发的超负荷的请求而完全崩溃，消息队列能够使关键组件顶住突发的访问压力
- 异步通信：消息队列允许用户把消息放到队列中但不立即处理它

##### 发布/订阅模式

- 一对多，生产者将消息发布到Topic中，有多个消费者订阅该主题，发布到Topic的消息会被所有订阅者消费，被消费的数据不会立即从Topic中清除

![image-20220511002535776](C:%5CUsers%5CAdministrator%5CAppData%5CRoaming%5CTypora%5Ctypora-user-images%5Cimage-20220511002535776.png)

##### 

