 # chapter2 线程安全性

## 什么是线程安全性

当多个线程访问某个状态变量并且其中有个线程进行写操作时，必须采用同步机制来协同这些线程对变量的访问。Java中的主要同步机制是关键字 synchronized，它提供了一种独占的加锁方式

- 同时，“同步”这个术语还包括volatile类型变量、显式锁（Explicit Lock）以及原子变量

**无状态对象一定是线程安全的。**大多数Servlet都是无状态的，从而极大地降低了在实现Servlet线程安全性时的复杂性。只有当Servlet在处理请求时需要保存一些信息，线程安全性才会成为一个问题

## 原子性

### 1、竞态条件

当某个计算的正确性取决于多个线程的交替执行时序时，就会发生竞态条件，即正确结果取决于运气。

大多数竞态条件的本质：基于一种可能失效的观察结果来做出判断或者执行某个计算

- 这种类型的竞态条件成为**“先检查后执行”**：首先观察到某个条件为真（例如文件X不存在），然后根据这个观察结果采用相应的动作（创建文件X），但事实上，在你观察到这个结果以及开始创建文件之前，观察结果可能变得无效（另一个线程在这期间创建了文件X），从而导致各种问题（未预期的异常、数据被覆盖、文件被破坏等）

### 2、延迟初始化中的竞态条件

使用“先检查后执行”的一种常见情况是延迟初始化，其目的是将对象的初始化操作推迟到实际被使用时才进行，同时确保只被初始化一次。

![image-20220517223443292](https://raw.githubusercontent.com/Lance-Mai/MyPictureBed/main/images/image-2022/image-20220517223443292.png)

![image-20220517223546941](https://raw.githubusercontent.com/Lance-Mai/MyPictureBed/main/images/image-2022/image-20220517223546941.png)



### 3、复合操作

为了确保线程安全性，“先检查后执行”和 “读取-修改-写入”等**操作必须是原子的**。我们将这些操作统称为复合操作：包含了一组必须以原子方式执行的操作以确保线程安全性

- 现在我们采用一个现有的线程安全类来实现线程安全（Java可以也通过内置的加锁机制来保证）

```java
@ThreadSafe
public class CountingFactorizer implements Servlet {
    private final AtomicLong count = new AtomicLong(0);
    public long getCount() { return count.get(); }
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        BigInteger[] factors = factor(i);
        count.incrementAndGet();
        encodeIntoResponse(resp, factors);
    }
}
```

 在java.util.concurrent.atomic 包中包含了一些原子变量类，用于实现在数值和对象引用上的原子状态转换。通过用 AtomicLong来代替long类型的计数器，能够确保所有对计数器状态的访问操作都是原子的。

## 加锁机制

当在Servlet中添加一个状态时，可以通过线程安全的对象来管理Servlet的状态以维护Servlet的线程安全性。但是想要在Servlet中添加更多的状态，是否只需要添加更多的线程安全状态变量就可以了呢？

![image-20220517225828667](https://raw.githubusercontent.com/Lance-Mai/MyPictureBed/main/images/image-2022/image-20220517225828667.png)

![image-20220517225904220](https://raw.githubusercontent.com/Lance-Mai/MyPictureBed/main/images/image-2022/image-20220517225904220.png)

### 内置锁

Java提供了一种内置的锁机制来支持原子性：**同步代码块**（Synchronized Block）。同步代码块包括两部分：

- 一个作为锁的对象引用
- 一个作为由这个锁保护的代码块

每个Java对象都可以用作一个实现同步的锁，这些锁被称为内置锁或者监视器锁。线程在进入同步代码块之前会自动获得锁，并且在退出同步代码块时自动释放锁，无论时通过正常的控制路径退出还是从代码块中抛出异常退出。获得内置锁的唯一途径是进入这个锁保护的同步代码块或方法。

以下代码块会引发**性能问题**，而不是线程安全性问题

```java
@ThreadSafe
public class SynchronizedFacorizer implements Servlet {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;
    public synchronized void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequest(req);
        if (i.equals(lastNumber)) {
            encodeIntoResponse(resp, lastFactors);
        } else {
            BigInteger[] factors = factor(i);
            lastNumber = i;
            lastFactors = factors;
            encodeIntoResponse(resp, factors);
        }
    }
}
```



### 重入

当某个线程请求一个由其他线程持有的锁，发出请求的线程就会阻塞。然而，由于内置锁是可重入的，因此如果某个线程试图获得一个已由它自己持有的锁，那么这个请求就会成功。

![image-20220518235956019](https://raw.githubusercontent.com/Lance-Mai/MyPictureBed/main/images/image-2022/image-20220518235956019.png)



## 用锁来保护状态

- 对于可能被多个线程同时访问的可变状态变量，在访问它是都需要持有同一个锁。在这种情况下，我们成状态变量是由这个锁保护的。
- 每个共享的和可变的变量都应该只由一个锁来保护，从而使维护人员知道是哪一个锁

## 活跃性和性能

 由于上面的例子中service是一个synchronized方法，因此每次只有一个线程可以执行，这就背离了Servlet框架的初衷：Servlet需要能同时处理多个请求，这在负载过高的情况下会给用户带来糟糕的体验。

以下图表示了当多个请求同时到达因数分解Servlet时发生的情况：这些请求将排队等待处理。我们将这种Web应用程序称为 不良并发（Poor Concurrency）应用程序：可同时调用的数量，不仅受到可用处理资源的限制，还受到应用程序本身结构的限制。

- 不过，我们可以通过缩小同步代码块的作用范围，做到既确保Servlet的并发性，同时又维护线程安全性。

![image-20220519002844593](https://raw.githubusercontent.com/Lance-Mai/MyPictureBed/main/images/image-2022/image-20220519002844593.png)



```java
@ThreadSafe
public class CacheFactorizer implements Servlet {
    @GuardedBy("this") private BigInteger lastNumber;
    @GuardedBy("this") private BigInteger[] lastFactors;
    @GuardedBy("this") private long hits;
    @GuardedBy("this") private long cacheHits;
    
    public synchronized long getHits(){ return hits; }
    public synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double)hits;
    }
    public void service(ServletRequest req, ServletResponse resp) {
        BigInteger i = extractFromRequst(req);
        BigInteger[] factors = null;
        synchronized (this) {
            ++hits;
            if (i.equals(lastNumber)) {
                ++cacheHits;
                factors = lastFactors.clone();
            }
        }
        if (factors == null) {
            factors = factor(i);
            synchronized (this) {
                lastNumber = i;
                lastFactors = factors.clone();
            }
        }
        encodeIntoResponse(resp, factors);
    }
}
```

![image-20220519005441998](https://raw.githubusercontent.com/Lance-Mai/MyPictureBed/main/images/image-2022/image-20220519005441998.png)































