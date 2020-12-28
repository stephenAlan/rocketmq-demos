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

    // 发送事务消息
    @RequestMapping("sendTranc")
    public String sendTranc() {
        String msg = "hello msg";
        TransactionSendResult sendResult = producer.sendTransaction("TopicTest", "Transaction_TAG", msg);
        // 本地操作放在这里,事务是无效的,要放在TransactionListenerImpl中
        // int i = 1 / 0;
        log.info("发送Transaction消息：{}",msg);
        return "hello send";
    }


}
