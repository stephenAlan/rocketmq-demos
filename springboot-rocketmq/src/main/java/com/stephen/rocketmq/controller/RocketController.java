package com.stephen.rocketmq.controller;

import com.stephen.rocketmq.common.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by ssc on 2020-12-26 22:47 .
 * Description:
 */
// @RestController
@Slf4j
@RestController
public class RocketController {

    @Resource
    private Producer producer;

    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }

    // 发送普通消息
    @RequestMapping("send")
    public String send() {
        String msg = "hello msg";
        producer.send("TopicTest","Simple_TAG",msg);
        log.info("发送消息：{}",msg);
        return "hello send";
    }

    // 发送延时消息
    // messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
    // 总共18个级别,设为3 表示10s之后发送
    @RequestMapping("sendDelay")
    public String sendDelay() {
        String msg = "hello delay msg";
        producer.sendDelay("TopicTest","Simple_TAG",msg,3);
        log.info("发送延时消息：{}",msg);
        return "hello send delay";
    }

    // 发送事务消息
    @RequestMapping("sendTranc")
    public String sendTranc() {
        String msg = "hello msg";
        Object obj = "REDAY TO MYSQL DATA";
        TransactionSendResult sendResult = producer.sendTransaction("TopicTest", "Transaction_TAG", msg, obj);
        // 本地操作放在这里,事务是无效的,要放在TransactionListenerImpl中
        // int i = 1 / 0;
        log.info("发送Transaction消息：{}",msg);
        return "hello send Transaction";
    }


}
