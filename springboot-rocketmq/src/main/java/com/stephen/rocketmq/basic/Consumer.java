package com.stephen.rocketmq.basic;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * Created by ssc on 2020-12-27 11:55 .
 * Description:
 */
@Component
@Slf4j
public class Consumer implements RocketMQListener<Message> {
    @Override
    public void onMessage(Message message) {

    }
}
