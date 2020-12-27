package com.stephen.rocketmq.controller;

import com.stephen.rocketmq.basic.SpringProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by ssc on 2020-12-26 22:47 .
 * Description:
 */
@RestController
public class RocketController {

    @Resource
    private SpringProducer springProducer;

    @RequestMapping("send")
    public String send() {
        springProducer.send("TopicTest","hello msg");
        return "hello send";
    }


}
