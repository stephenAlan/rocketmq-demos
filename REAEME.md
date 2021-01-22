# 本工程包含三个项目
## rocketmq-api
rocketmq的一些基础操作

## rocketmq-console 
rocketmq的可视化监控项目

## springboot-rocketmq
rocketmq与springboot整合

版本：rocketmq4.7.1

- 主要依赖
  - 该依赖对应的底层rocketmq版本正好是4.7.1
``` 
<dependency>
     <groupId>org.apache.rocketmq</groupId>
     <artifactId>rocketmq-spring-boot-starter</artifactId>
     <version>2.1.1</version>
 </dependency>
```
- 发送消息类型
  - 普通消息
  - 延时消息
    - 默认18个延时级别,可自定义时间或者增加个数
  - 事务消息
    - 通过实现RocketMQLocalTransactionListener中的两个方法来实现事务监控
    - executeLocalTransaction -- 执行本地事务,正常返回COMMIT,异常返回UNKNOW或ROLLBACK
    - checkLocalTransaction   -- 回查本地事务，只有执行本地事务返回UNKNOW状态的才会进行回查